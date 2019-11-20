package online.shixun.project.module.member.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import online.shixun.project.module.member.service.CartItemService;
import online.shixun.project.module.member.service.MemberService;

/**
 * 购物车控制器类
 * @author am
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private MemberService memberService;
	
	/**
	 * 购物车页面
	 * @param model
	 * @param principal
	 * @return
	 */
	@RequestMapping("")
	public String cart(Model model, Principal principal) {
		// 获取当前用户的用户名
		String username = principal.getName();
		model.addAttribute("username", username);
		// 获取当前用户购物车数量
		model.addAttribute("cartCount", cartItemService.getCartCountByUsername(username));
		// 获取当前用户购物车项集合
		model.addAttribute("cartItems", cartItemService.getCartItemByUsername(username));
		return "member/cart";
	}
	
	/**
	 * 修改购物车项商品数量
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Integer update(Long id, Long commodityId, Integer count, String parameter, Principal principal) {
		// 构造参数JSON
		if (parameter != null && parameter.length() > 0) {
			JSONArray jsonArray = new JSONArray();
			String[] parameters = parameter.split(",");
			for (String p : parameters) {
				String[] nameAndParameter = p.split("-");
				JSONObject nameJson = new JSONObject();
				JSONObject parameterJson = new JSONObject();
				nameJson.put("name", nameAndParameter[0]);
				parameterJson.put("name", nameAndParameter[1]);
				nameJson.put("parameter", parameterJson);
				jsonArray.add(nameJson);
			}
			parameter = jsonArray.toString();
		}
		// 获取当前用户的用户名
		String username = principal.getName();
		Long memberId = memberService.getMemberByUsername(username).getId();
		// 修改商品数量
		cartItemService.updateCartItem(commodityId, count, memberId, parameter);
		// 重新查询该购物车项的商品数量
		Integer reCount = cartItemService.getCartItemCommodityCountById(id, commodityId, username);
		return reCount;
	}
	
	/**
	 * 删除购物车项
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(Long id, Principal principal) {
		// 获取当前用户的用户名
		String username = principal.getName();
		// 删除购物车项
		Boolean result = cartItemService.deleteCartItemById(id, username);
		if (result) {
			// 删除成功
			Integer reCount = cartItemService.getCartCountByUsername(username);
			JSONObject json = new JSONObject();
			json.put("code", "success");
			json.put("reCount", reCount);
			return json.toString();
		} else {
			// 删除失败
			JSONObject json = new JSONObject();
			json.put("code", "fail");
			return json.toString();
		}
	}
	
	/**
	 * 添加购物车
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(Long commodityId, Integer count, String parameter, Principal principal) {
		
		// 获取当前登录的会员ID
		String username = principal.getName();
		Long memberId = memberService.getMemberByUsername(username).getId();
		
		if (count < 1 || count > 999) {
			JSONObject json = new JSONObject();
			json.put("code", "fail");
			return json.toString();
		}
		
		if (cartItemService.addCartItem(commodityId, count, memberId, parameter, username)) {
			// 添加成功
			// 请求购物车数量
			Integer reCount = cartItemService.getCartCountByUsername(username);
			JSONObject json = new JSONObject();
			json.put("code", "success");
			json.put("reCount", reCount);
			return json.toString();
		}
		JSONObject json = new JSONObject();
		json.put("code", "fail");
		return json.toString();
	}
}
