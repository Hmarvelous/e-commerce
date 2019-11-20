package online.shixun.project.module.order.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import online.shixun.project.module.order.dto.OrderItemDto;
import online.shixun.project.module.order.enumerate.OrderStatusEnum;

/**
 * 
 * @author am
 *
 */
@Mapper
public interface OrderItemMapper {
	
	/**
	 * 插入订单项
	 * @param orderItem 订单项对象
	 * @return 返回1为成功  返回0为失败
	 */
	Integer insertOrderItem(@Param("orderItem") OrderItemDto orderItem);
	
	/**
	 * 根据订单号查询订单项
	 * @param orderNumber 订单号
	 * @param username 用户名
	 * @return 订单项集合
	 */
	List<OrderItemDto> selectOrderItemByOrderNumber(@Param("orderNumber") Long orderNumber, @Param("username") String username);
	
	/**
	 * 更新订单项状态
	 * @param commodityId 商品ID
	 * @param orderId 订单ID
	 * @param orderItemStatus 订单状态
	 * @return 返回1为成功  返回0为失败
	 */
	Integer updateOrderItemStatus(@Param("commodityId") Long commodityId, @Param("orderId") Long orderId, @Param("orderItemStatus") OrderStatusEnum orderItemStatus);
}
