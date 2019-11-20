package online.shixun.project.module.commodity.controller;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import online.shixun.project.module.commodity.dto.CommentDto;
import online.shixun.project.module.commodity.service.CommentService;
import online.shixun.project.module.member.dto.MemberDto;
import online.shixun.project.module.member.service.MemberService;

/**
 * 商品评论控制器类
 * @author am
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private MemberService memberService;
	
	/**
	 * 获取评论（JSON）
	 * @param level
	 * @param commodityId
	 * @param pageNow
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping("/getComments")
	@ResponseBody
	public String getComments(Integer level, Long commodityId, Integer pageNow, Integer pageSize, Model model) {
		
		// 查询全部评论信息时使用
		if (level != null && level == -1) {
			level = null;
		}
		PageInfo<CommentDto> commentPageInfo = commentService.getCommentsPage(level, commodityId, pageNow, pageSize);
		// 当查询不到数据时
		if (commentPageInfo == null) {
			return "{\"code\": \"error\"}";
		}
		return JSONObject.toJSONString(commentPageInfo);
	}
	
	/**
	 * 添加评论
	 * @return
	 */
	@RequestMapping("/addComment")
	@ResponseBody
	public String addComment(@RequestParam("orderNumber") Long orderNumber,
							 @RequestParam("commodityId") Long commodityId,
							 @RequestParam("content") String content,
							 @RequestParam(value = "commentLevel", defaultValue = "1") Integer commentLevel,
							 Principal principal) {
		JSONObject json = new JSONObject();
		String username = principal.getName();
		MemberDto member = memberService.getMemberByUsername(username);
		// 添加评论信息
		CommentDto comment = new CommentDto(content, member, new Date(), commentLevel);
		if (commentService.addCommentByCommentId(orderNumber, commodityId, comment, member)) {
			json.put("code", "success");
			return json.toString();
		}
		json.put("code", "fail");
		return json.toString();
	}
}
