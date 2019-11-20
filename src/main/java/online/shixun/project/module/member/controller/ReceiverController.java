package online.shixun.project.module.member.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import online.shixun.project.module.member.service.ReceiverService;

/**
 * 收货地址控制器类
 * @author am
 *
 */
@Controller
@RequestMapping("/receiver")
public class ReceiverController {

	@Autowired
	private ReceiverService receiverService;
	
	
	/**
	 * 获取地址信息
	 * @param id 地址ID
	 * @return
	 */
	@RequestMapping("/getAddress")
	@ResponseBody
	public String getAddress(@RequestParam("address_id") Long id, Principal principal) {
		return JSONObject.toJSONString(receiverService.getReceiverByID(id, principal.getName()));
	}
}
