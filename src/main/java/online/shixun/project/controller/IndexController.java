package online.shixun.project.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import online.shixun.project.module.advertise.service.CarouselAdService;
import online.shixun.project.module.commodity.service.CommodityService;
import online.shixun.project.module.commodity.service.SortService;
import online.shixun.project.module.member.service.CartItemService;

/**
 * 首页控制器类
 * @author am
 *
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	private CarouselAdService carouselAdService;
	@Autowired
	private SortService sortService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private CartItemService cartItemService;
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping(value = {"/", "/index"})
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
		
		// 获取六条轮播广告
		model.addAttribute("carouselAds", carouselAdService.getSixEffectiveCarouselAds());
		// 获取商品分类项
		model.addAttribute("sorts", sortService.getAllSorts());
		// 获取五条热门商品信息
		model.addAttribute("hotCommoditys", commodityService.getHotFivesCommoditys());
		// 获取五条最新商品信息
		model.addAttribute("newCommoditys", commodityService.getNewFivesCommoditys());
		// 获取五条精品商品信息
		model.addAttribute("boutiqueCommoditys", commodityService.getBoutiqueFivesCommoditys());
		// 获取八条家具商品
		
		// 获取八条家居商品
		
		// 获取八条户外出行商品
		
		// 获取十五条可能喜欢商品
		
		return "index";
	}
}
