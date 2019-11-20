package online.shixun.project.module.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import online.shixun.project.module.member.dto.CartItemDto;
import online.shixun.project.module.member.mapper.CartItemMapper;
import online.shixun.project.module.member.service.CartItemService;

/**
 * 购物车项服务接口实现类
 * @author am
 *
 */
@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemMapper cartItemMapper;
	
	/**
	 * 根据用户名查询购物车数量
	 */
	@Override
	public Integer getCartCountByUsername(String username) {
		return cartItemMapper.selectCartCountByUsername(username);
	}

	/**
	 * 根据用户名查询用户购物车项集合
	 */
	@Override
	public List<CartItemDto> getCartItemByUsername(String username) {
		return cartItemMapper.selectCartItemByUsername(username);
	}

	/**
	 * 根据购物车项ID查询商品数量
	 */
	@Override
	public Integer getCartItemCommodityCountById(Long id, Long commodityId, String username) {
		return cartItemMapper.selectCartItemCommodityCountById(id, commodityId, username);
	}

	/**
	 * 根据购物车项ID删除购物车项
	 */
	@Override
	public Boolean deleteCartItemById(Long id, String username) {
		return cartItemMapper.deleteCartItemById(id, username) == 1;
	}

	/**
	 * 添加商品到购物车
	 */
	@Override
	@Transactional
	public Boolean addCartItem(Long commodityId, Integer count, Long memberId, String parameter, String username) {
		// 构造选定参数JSON
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
		// 判断该用户的购物车是否以及拥有该商品(参数一定要相同,不同参数视为不同购物车项)
		CartItemDto cartItem = cartItemMapper.selectCartItemByCommodityAndMember(commodityId, memberId, parameter);
		if (cartItem != null) {
			// 已经拥有该商品,更新商品数量并更新选定参数
			cartItem.setCount(cartItem.getCount() + count);
			if (!updateCartItem(commodityId, cartItem.getCount(), memberId, parameter)) {
				// 更新失败
				return false;
			}
		} else {
			// 没有该商品,直接添加购物车项并添加选定参数
			// 添加购物车项
			if (cartItemMapper.addCartItem(commodityId, count, memberId, parameter) != 1) {
				// 添加失败
				return false;
			}
		}
		
		return true;
	}

	/**
	 * 更新购物车项
	 */
	@Override
	public Boolean updateCartItem(Long commodityId, Integer count, Long memberId, String parameter) {
		return cartItemMapper.updateCartItem(commodityId, count, memberId, parameter) == 1;
	}
	
}
