package online.shixun.project.module.spike.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import online.shixun.project.module.commodity.service.CommentService;
import online.shixun.project.module.member.service.CartItemService;
import online.shixun.project.module.spike.dto.SpikeCommodityDto;
import online.shixun.project.module.spike.service.SpikeCommodityService;
import online.shixun.project.module.spike.service.SpikeSessionService;
import online.shixun.project.rabbit.RabbitConfig;

/**
 * 秒杀控制器
 * @ClassName: SpikeController.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: am
 * @date: 2019年10月26日 下午2:54:52
 */
@Controller
@RequestMapping("/spike")
public class SpikeController {

	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private SpikeSessionService spikeSessionService;
	@Autowired
	private SpikeCommodityService spikeCommodityService;
	@Autowired
	private CommentService commentService;
	
	// Redis缓存
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	// RabbitMQ消息队列
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * 秒杀首页
	 * @return
	 */
	@RequestMapping("/spikeDetails")
	public String index(Model model, Principal principal) {
		// 获取当前用户信息
		if (principal != null) {
			// 该用户已认证
			// 获取当前用户的用户名
			String username = principal.getName();
			model.addAttribute("username", username);
			// 获取当前用户购物车数量
			model.addAttribute("cartCount", cartItemService.getCartCountByUsername(username));
		}
		
		return "spike/spike";
	}
	
	/**
	 * 前往商品详情页面
	 * @return
	 */
	@RequestMapping("/spikeDetails/{commodityId}")
	public String spikeDetails(@PathVariable(value = "commodityId") Long commodityId, Model model, Principal principal) {
		// 获取当前用户信息
		if (principal != null) {
			// 该用户已认证
			// 获取当前用户的用户名
			String username = principal.getName();
			model.addAttribute("username", username);
			// 获取当前用户购物车数量
			model.addAttribute("cartCount", cartItemService.getCartCountByUsername(username));
		}
		// 通过商品ID查询秒杀商品信息
		SpikeCommodityDto spikeCommodity = spikeCommodityService.getSpikeCommodityById(commodityId);
		// 判断活动时间是否货期
		long timeDifference = (spikeCommodity.getStartTime().getTime() - System.currentTimeMillis());
		if (-timeDifference >= 30*60*1000) {
			return "redirect:/spike/spikeDetails";
		}
		model.addAttribute("spikeCommodity", spikeCommodity);
		// 获取各级别评论数量
		// 全部
		model.addAttribute("allComment", commentService.getAllCommentsCountByLevel(null, commodityId));
		// 好评
		model.addAttribute("praise", commentService.getAllCommentsCountByLevel(0, commodityId));
		// 中评
		model.addAttribute("average", commentService.getAllCommentsCountByLevel(1, commodityId));
		// 差评
		model.addAttribute("badReview", commentService.getAllCommentsCountByLevel(2, commodityId));
		return "spike/details";
	}
	
	/**
	 * 获取全部有效的秒杀活动场次
	 * @return
	 */
	@RequestMapping("/spikeSession")
	@ResponseBody
	public JSONArray getSpikeSession() {
		// 查询有效的秒杀活动场次
		return (JSONArray) JSONArray.toJSON(spikeSessionService.getAllSpikeSession());
	}
	
	
	
	/**
	 * 初始化秒杀商品数量到Redis
	 */
	@PostConstruct
	public void init() {
		List<SpikeCommodityDto> spikeCommoditys = spikeCommodityService.getAllCommoditys();
		for (SpikeCommodityDto spikeCommodity : spikeCommoditys) {
			redisTemplate.opsForValue().set("spike_count_" + spikeCommodity.getId(), spikeCommodity.getStore());
		}
	}
	
	
	/**
	 * 秒杀
	 * @return
	 */
	@RequestMapping("/start")
	@ResponseBody
	public JSONObject start(@RequestParam("id") long id,
			            @RequestParam("parameters") String parameters,
			            Principal principal) {
		JSONObject json = new JSONObject();
		
		// 初步判断该商品库存是否充足
		if ((long)redisTemplate.opsForValue().decrement("spike_count_" + id) < 0) {
			redisTemplate.opsForValue().increment("spike_count_" + id);
			// 商品已售完
			json.put("status", "complete");
			return json;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commodityId", id);
		map.put("parameters", parameters);
		map.put("username", principal.getName());
		
		// 添加请求到队列中
		rabbitTemplate.convertAndSend(RabbitConfig.SECKILL_QUEUE, map);
		json.put("status", "wait");
		return json;
	}
	
	
	/**
	 * 查询Redis状态订单
	 * @return
	 */
	@RequestMapping("/state")
	@ResponseBody
	public JSONObject stateOrder(@RequestParam("id") long id, Principal principal) {
		return (JSONObject) redisTemplate.opsForValue().get("state_order_" + id + "_" + principal.getName());
	}
	
	/**
	 * 取消秒杀付款
	 * @return
	 */
	@RequestMapping("/cancelSeckill")
	@ResponseBody
	public JSONObject cancelSeckillPayment(@RequestParam("id") long id, Principal principal) {
		JSONObject json = new JSONObject();
		if (redisTemplate.delete("state_order_" + id + "_" + principal.getName())) {
			// 未完成付款,Redis商品库存自增1
			redisTemplate.opsForValue().increment("spike_count_" + id);
			// 增加数据库的库存
			spikeCommodityService.updateSpikeCommodityStockincrement(id);
			json.put("result", "success");
			return json;
		}
		json.put("result", "fail");
		return json;
	}
	
	
	/**
	 * 查询Redis商品库存
	 * @return
	 */
	@RequestMapping("/seckillCommodityCount")
	@ResponseBody
	public JSONObject seckillCommodityCount(@RequestParam("id") long id) {
		JSONObject json = new JSONObject();
		Integer count = (Integer) redisTemplate.opsForValue().get("spike_count_" + id);
		if (count == null) {
			json.put("count", 0);
			return json;
		}
		json.put("count", count);
		return json;
	}
}
