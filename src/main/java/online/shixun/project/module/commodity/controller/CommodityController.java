package online.shixun.project.module.commodity.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import online.shixun.project.module.commodity.service.BrandService;
import online.shixun.project.module.commodity.service.CommentService;
import online.shixun.project.module.commodity.service.CommodityService;
import online.shixun.project.module.commodity.service.SortService;
import online.shixun.project.module.member.service.CartItemService;

/**
 * 商品控制器类
 * @author am
 *
 */
@Controller
@RequestMapping("/commodity")
public class CommodityController {

	@Autowired
	private CommodityService commodityService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private SortService sortService;
	
	/**
	 * 商品详情页面
	 * @return
	 */
	@RequestMapping("/details")
	public String details(Long id, Model model, Principal principal) {
		
		// 判断当前用户是否登录
		if (principal != null) {
			// 获取当前用户的用户名
			String username = principal.getName();
			model.addAttribute("username", username);
			// 获取当前用户购物车数量
			model.addAttribute("cartCount", cartItemService.getCartCountByUsername(username));
		}
		
		// 根据商品ID查询商品详细信息
		model.addAttribute("commodity", commodityService.getDetailsCommodityById(id));
		// 获取各级别评论数量
		// 全部
		model.addAttribute("allComment", commentService.getAllCommentsCountByLevel(null, id));
		// 好评
		model.addAttribute("praise", commentService.getAllCommentsCountByLevel(0, id));
		// 中评
		model.addAttribute("average", commentService.getAllCommentsCountByLevel(1, id));
		// 差评
		model.addAttribute("badReview", commentService.getAllCommentsCountByLevel(2, id));
		
		return "commodity/details";
	}
	
	/**
	 * 商品搜索页面
	 * @return
	 */
	@RequestMapping("/search")
	public String search(@RequestParam(value = "keyword", defaultValue = "") String keyword,
						 @RequestParam(value = "page", defaultValue = "1") Integer page,
						 @RequestParam(value = "size", defaultValue = "20") Integer size,
						 @RequestParam(value = "brand", defaultValue = "0") Long brandId,
						 Model model, Principal principal) {
		if (keyword.length() == 0) {
			keyword = null;
		}
		if (brandId == 0) {
			brandId =  null;
		}
		// 判断当前用户是否登录
		if (principal != null) {
			// 获取当前用户的用户名
			String username = principal.getName();
			model.addAttribute("username", username);
			// 获取当前用户购物车数量
			model.addAttribute("cartCount", cartItemService.getCartCountByUsername(username));
		}
		model.addAttribute("keyword", keyword);
		model.addAttribute("brand", brandId);
		// 获取商品品牌信息
		model.addAttribute("brands", brandService.getAllBrands());
		// 根据关键字获取商品信息
		model.addAttribute("commoditys", commodityService.getCommoditysByKeyword(keyword, page, size, brandId));
		return "search";
	}
	
	/**
	 * 商品分类页面
	 * @return
	 */
	@RequestMapping("/sort")
	public String sort(@RequestParam(value = "primaryId", defaultValue = "1") Long primaryId,
					   @RequestParam(value = "minorId", defaultValue = "17") Long minorId,
					   @RequestParam(value = "sortId", defaultValue = "-1") Long sortId,
					   @RequestParam(value = "page", defaultValue = "1") Integer page,
					   @RequestParam(value = "size", defaultValue = "15") Integer size,
					   Model model, Principal principal) {
		// 获取当前用户信息
		if (principal != null) {
			// 该用户已认证
			// 获取当前用户的用户名
			String username = principal.getName();
			model.addAttribute("username", username);
			// 获取当前用户购物车数量
			model.addAttribute("cartCount", cartItemService.getCartCountByUsername(username));
		}
		// 获取二级分类ID
		if (minorId == 17) {
			minorId = sortService.getSortIdByParentId(primaryId);
		}
		
		// 获取二级菜单
		model.addAttribute("secondarySort", sortService.getSortsByParentId(primaryId));
		model.addAttribute("primaryId", primaryId);
		// 获取三级菜单
		model.addAttribute("thirdSort", sortService.getSortsByParentId(minorId));
		model.addAttribute("minorId", minorId);
		// 根据分类ID获取商品信息
		model.addAttribute("sortId", sortId);
		if (sortId == -1) {
			sortId = null;
		}
		model.addAttribute("commodityPage", commodityService.getPageCommodityBySortId(sortId, minorId, page, size));
		return "commodity/sort";
	}
	
}
