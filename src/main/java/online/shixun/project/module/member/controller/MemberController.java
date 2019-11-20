package online.shixun.project.module.member.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import online.shixun.project.module.member.dto.MemberDto;
import online.shixun.project.module.member.service.CartItemService;
import online.shixun.project.module.member.service.MemberService;

/**
 * 会员控制器
 * @author am
 *
 */
@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private MemberService memberService;
	
	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Integer code, Model model, Principal principal) {
		// 获取当前用户信息
		if (principal != null) {
			// 该用户已认证
			// 获取当前用户的用户名
			String username = principal.getName();
			model.addAttribute("username", username);
			// 获取当前用户购物车数量
			model.addAttribute("cartCount", cartItemService.getCartCountByUsername(username));
		}
		
		if (code != null && code == 201) {
			model.addAttribute("code", "登陆失败");
		}
		return "login";
	}
	
	/**
	 * 注册界面
	 * @return
	 */
	@RequestMapping("/register")
	public String register(Model model, Principal principal) {
		// 获取当前用户信息
		if (principal != null) {
			// 该用户已认证
			// 获取当前用户的用户名
			String username = principal.getName();
			model.addAttribute("username", username);
			// 获取当前用户购物车数量
			model.addAttribute("cartCount", cartItemService.getCartCountByUsername(username));
		}
		return "register";
	}
	
	/**
	 * 处理注册
	 * @return
	 */
	@PostMapping("/doRegister")
	@ResponseBody
	public JSONObject doRegister(MemberDto member) {
		JSONObject json = new JSONObject();
		
		// 根据用户名查询用户信息,判断用户是否存在
		MemberDto md = memberService.getMemberByUsername(member.getUsername());
		if (md != null) {
			json.put("result", "alreadyExist");
			return json;
		}
		// 注册
		if (memberService.registerMember(member)) {
			json.put("result", "success");
			return json;
		}
		json.put("result", "fail");
		return json;
	}
}
