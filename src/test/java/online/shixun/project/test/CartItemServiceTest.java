package online.shixun.project.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import online.shixun.project.module.member.dto.CartItemDto;
import online.shixun.project.module.member.service.CartItemService;

/**
 * 
 * @author am
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CartItemServiceTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CartItemService cartItemService;
	
	/**
	 * 测试根据用户名查询购物车数量
	 */
	@Test
	public void getCartCountByUsername() {
		Integer count = cartItemService.getCartCountByUsername("test1");
		logger.info(count.toString());
	}
	
	/**
	 * 测试根据用户名查询用户购物车项集合
	 */
	@Test
	public void getCartItemByUsername() {
		List<CartItemDto> cartItems = cartItemService.getCartItemByUsername("test1");
		logger.info(cartItems.toString());
	}
	
	/**
	 * 测试根据购物车项ID查询商品数量
	 */
	@Test
	public void getCartItemCommodityCountById() {
		Integer count = cartItemService.getCartItemCommodityCountById(1L, 2L, "test1");
		logger.info(count.toString());
	}
	
	/**
	 * 删除购物车项
	 */
	@Test
	public void delete() {
		Boolean result = cartItemService.deleteCartItemById(1L, "test1");
		logger.info(result.toString());
	}
}
