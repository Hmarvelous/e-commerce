package online.shixun.project.module.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import online.shixun.project.module.member.dto.CartItemDto;

/**
 * 
 * @author am
 *
 */
@Mapper
public interface CartItemMapper {

	/**
	 * 根据用户名查询购物车数量
	 * @param username 用户名
	 * @return 该用户购物车总商品数
	 */
	Integer selectCartCountByUsername(@Param("username") String username);
	
	/**
	 * 根据用户名查询用户购物车项集合
	 * @param username 用户名
	 * @return 该用户所有购物车集合
	 */
	List<CartItemDto> selectCartItemByUsername(@Param("username") String username);
	
	/**
	 * 根据购物车项ID查询商品数量
	 * @param id 购物车项ID
	 * @param commodityId 商品ID
	 * @param username 当前用户的用户名
	 * @return 购物车项商品数量
	 */
	Integer selectCartItemCommodityCountById(@Param("id") Long id, @Param("commodityId") Long commodityId, @Param("username") String username);
	
	/**
	 * 根据购物车项ID删除购物车项
	 * @param id 购物车项ID
	 * @param username 当前用户的用户名
	 * @return 购物车项商品数量
	 */
	Integer deleteCartItemById(@Param("id") Long id, @Param("username") String username);
	
	/**
	 * 根据商品ID以及用户ID查询该用户购物车项
	 * @param commodityId 商品ID
	 * @param memberId 用户ID
	 * @param parameter 选定参数
	 * @return 购物车项对象
	 */
	CartItemDto selectCartItemByCommodityAndMember(@Param("commodityId") Long commodityId, @Param("memberId") Long memberId, @Param("parameter") String parameter);
	
	/**
	 * 添加购物车项
	 * @param commodityId 商品ID
	 * @param count 商品数量
	 * @param memberId 用户ID
	 * @param parameter 选定参数JSON
	 * @return 1为成功 0为失败
	 */
	Integer addCartItem(@Param("commodityId") Long commodityId, @Param("count") Integer count, @Param("memberId") Long memberId, @Param("parameter") String parameter);
	
	/**
	 * 更新购物车项
	 * @param commodityId 商品ID
	 * @param count 商品数量
	 * @param memberId 用户ID
	 * @param parameter 选定参数JSON
	 * @return 1为成功 0为失败
	 */
	Integer updateCartItem(@Param("commodityId") Long commodityId, @Param("count") Integer count, @Param("memberId") Long memberId, @Param("parameter") String parameter);
}
