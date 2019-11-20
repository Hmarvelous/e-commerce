package online.shixun.project.module.order.controller;

import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import online.shixun.project.module.commodity.service.CommodityService;
import online.shixun.project.module.member.service.CartItemService;
import online.shixun.project.module.member.service.MemberService;
import online.shixun.project.module.member.service.ReceiverService;
import online.shixun.project.module.order.dto.OrderDto;
import online.shixun.project.module.order.dto.OrderItemDto;
import online.shixun.project.module.order.enumerate.OrderStatusEnum;
import online.shixun.project.module.order.service.OrderItemService;
import online.shixun.project.module.order.service.OrderService;

/**
 * 订单控制器类
 * @author am
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private ReceiverService receiverService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 订单首页
	 * @return
	 */
	@RequestMapping("")
	public String index(Model model, Principal principal) {
		// 获取当前用户的用户名
		String username = principal.getName();
		model.addAttribute("username", username);
		// 获取当前用户购物车数量
		model.addAttribute("cartCount", cartItemService.getCartCountByUsername(username));
		// 获取各级别订单数量
		// 全部
		model.addAttribute("allOrderCount", orderService.getOrderCountByOrderStatus(null, username));
		// 待付款
		model.addAttribute("pendingPayment", orderService.getOrderCountByOrderStatus(OrderStatusEnum.PENDINGPAYMENT, username));
		// 待发货
		model.addAttribute("pendingShip", orderService.getOrderCountByOrderStatus(OrderStatusEnum.PENDINGSHIP, username));
		// 待收货
		model.addAttribute("pendingReceipt", orderService.getOrderCountByOrderStatus(OrderStatusEnum.PENDINGRECEIPT, username));
		// 待评价
		model.addAttribute("pendingEvaluation", orderService.getOrderCountByOrderStatus(OrderStatusEnum.PENDINGEVALUATION, username));
		return "order/order";
	}
	
	
	/**
	 * 获取订单(JSON)
	 * @param orderStatus  订单状态
	 * @param page 页码
	 * @param size 页码数据大小
	 * @return
	 */
	@RequestMapping("/getOrder")
	@ResponseBody
	public String getOrder(@RequestParam("orderStatus") Integer orderStatus,
						   @RequestParam("page") Integer page,
						   @RequestParam("size") Integer size,
						   Principal principal) {
		// 获取订单状态枚举
		OrderStatusEnum ose = OrderStatusEnum.get(orderStatus);
		return JSONObject.toJSONString(orderService.getOrderByOrderStatus(page, size, ose, principal.getName()));
	}
	
	/**
	 * 确认订单页面
	 * @return
	 */
	@RequestMapping("/confirm_order")
	public String settlement(@RequestParam("parameter") String parameter, Model model, Principal principal) {
		if (parameter == null || parameter.split("_").length <= 1) {
			// 跳转到出错页面
			return "redirect:/index";
		}
		// 获取当前登录用户的用户名
		String username = principal.getName();
		model.addAttribute("username", username);
		// 获取当前用户购物车数量
		model.addAttribute("cartCount", cartItemService.getCartCountByUsername(username));
		// 根据用户名获取该用户的四条收货地址
		model.addAttribute("receivers", receiverService.getFourReceiverByUsername(principal.getName()));
		// 根据参数获取商品详细信息,参数只有选中项
		List<OrderItemDto> orders = orderItemService.getOrderCommodityByParameter(parameter, commodityService, username);
		model.addAttribute("orders", orders);
		
		// 根据参数计算商品总价
		double actualAmount = 0.0;
		for (OrderItemDto order : orders) {
			actualAmount += order.getActualAmount();
		}
		model.addAttribute("actualAmount", actualAmount);
		return "order/confirm_order";
	}
	
	/**
	 * 生成订单
	 * @return
	 */
	@RequestMapping("/generateOrder")
	@ResponseBody
	public String generateOrder(@RequestParam("parameter") String parameter, Principal principal) {
		JSONObject json = new JSONObject();
		String username = principal.getName();
		// 创建订单
		OrderDto order = orderService.generateOrders(parameter,
				                        memberService.getMemberByUsername(username),
				                        receiverService.getReceiverByID(JSONObject.parseObject(parameter).getLong("receiver_id"),
		                        		username));
		if (order.getOrderNumber() != null && order.getOrderNumber() >= 100000000000000000L) {
			// 创建成功
			json.put("code", "success");
			// 查询当前会员余额
			json.put("balance", memberService.getMemberByUsername(username).getBalance());
			// 当前订单总金额
			json.put("totalAmount", order.getTotalAmount());
			// 刚刚生成订单的单号
			json.put("orderNumber", order.getOrderNumber().toString());
			return json.toString();
		}
		
		json.put("code", "fail");
		return json.toString();
	}
	
	
	/**
	 * 订单付款
	 * @return
	 */
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	@ResponseBody
	public String orderPayment(@RequestParam("orderNumber") Long orderNumber, Principal principal) {
		JSONObject json = new JSONObject();
		if (orderService.orderPayment(orderNumber, memberService.getMemberByUsername(principal.getName()))) {
			json.put("code", "success");
			
			Set<String> keysList = redisTemplate.keys("state_order_*_" + principal.getName());
			Iterator<String> kl = keysList.iterator();
			// 判断Redis是否存在状态订单
			if (kl.hasNext()) {
				String key = kl.next();
				JSONObject j = (JSONObject) redisTemplate.opsForValue().get(key);
				System.out.println(j);
				// 修改Radis状态订单
				j.put("result", "已付款");
				redisTemplate.opsForValue().set(key, j, 60*30, TimeUnit.SECONDS);
			}
			
			return json.toString();
		}
		json.put("code", "fail");
		return json.toString();
	}
	
	/**
	 * 获取余额信息
	 * @return
	 */
	@RequestMapping("/getBalance")
	@ResponseBody
	public String getBalance(@RequestParam("orderNumber") Long orderNumber, Principal principal) {
		JSONObject json = new JSONObject();
		if (orderNumber == null) {
			json.put("code", "fail");
			return json.toString();
		}
		
		// 查询余额及订单等信息
		json.put("balance", memberService.getMemberByUsername(principal.getName()).getBalance());
		json.put("order", orderService.getOrderByOrderNumber(orderNumber, principal.getName()));
		return json.toString();
	}
	
	
	/**
	 * 添加订单至回收站
	 * @return
	 */
	@RequestMapping("/recycleBin")
	public String recycleBin(@RequestParam("orderNumber") Long orderNumber, Principal principal) {
		// 更新订单状态
		orderService.toRecycleBinOrderByOrderNumber(orderNumber, memberService.getMemberByUsername(principal.getName()));
		return "redirect:/order";
	}
	
	
	/**
	 * 点击收货
	 * @return
	 */
	@RequestMapping("/receipt")
	@ResponseBody
	public String rreceipt(@RequestParam("orderNumber") Long orderNumber, Principal principal) {
		JSONObject json = new JSONObject();
		// 根据订单号修改订单状态
		if (orderService.orderReceipt(orderNumber, memberService.getMemberByUsername(principal.getName()))) {
			json.put("code", "success");
			return json.toString();
		}
		json.put("code", "fail");
		return json.toString();
	}
	
	
	/**
	 * 订单催促
	 * @return
	 */
	@RequestMapping("/urge")
	@ResponseBody
	public JSONObject urge(@RequestParam("orderNumber") Long orderNumber, Principal principal) {
		JSONObject json = new JSONObject();
		if (orderService.updateOrderUpdateTime(orderNumber,new Date(), principal.getName(), 1)) {
			json.put("result", "success");
			return json;
		}
		json.put("result", "fail");
		return json;
	}
}
