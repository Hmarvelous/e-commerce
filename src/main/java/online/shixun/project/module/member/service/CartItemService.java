package online.shixun.project.module.member.service;

import java.util.List;

import online.shixun.project.module.member.dto.CartItemDto;

/**
 * 购物车项服务接口类
 * @author am
 *
 */
public interface CartItemService {

	/**
	 * 根据用户名查询购物车数量
	 * @param username 用户名
	 * @return 用户购物车商品总数量
	 */
	Integer getCartCountByUsername(String username);
	
	/**
	 * 根据用户名查询用户购物车项集合
	 * @param username 用户名
	 * @return 该用户所有购物车集合
	 */
	List<CartItemDto> getCartItemByUsername(String username);
	
	/**
	 * 根据购物车项ID查询商品数量
	 * @param id 购物车项ID
	 * @param commodityId 商品ID
	 * @param username 当前用户的用户名
	 * @return 购物车项商品数量
	 */
	Integer getCartItemCommodityCountById(Long id, Long commodityId, String username);
	
	/**
	 * 根据购物车项ID删除购物车项
	 * @param id 购物车项ID
	 * @param username 当前用户的用户名
	 * @return 购物车项商品数量
	 */
	Boolean deleteCartItemById(Long id, String username);
	
	/**
	 * 添加商品到购物车
	 * @param commodityId 商品ID
	 * @param count 商品数量
	 * @param memberId 会员ID
	 * @param parameters 商品选定参数
	 * @param username 用户名
	 * @return 添加是否成功
	 */
	Boolean addCartItem(Long commodityId, Integer count, Long memberId, String parameters, String username);
	
	/**
	 * 更新购物车项
	 * @param commodityId 商品ID
	 * @param count 商品数量
	 * @param memberId 用户ID
	 * @param parameter 选定参数JSON
	 * @return 修改是否成功
	 */
	Boolean updateCartItem(Long commodityId, Integer count, Long memberId, String parameter);
}
