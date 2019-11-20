package online.shixun.project.module.order.service;


import java.util.List;

import online.shixun.project.module.commodity.service.CommodityService;
import online.shixun.project.module.order.dto.OrderItemDto;
import online.shixun.project.module.order.enumerate.OrderStatusEnum;

/**
 * 订单服务接口类
 * @author am
 *
 */
public interface OrderItemService {
	
	
	/**
	 * 通过参数获取订单商品信息(与通过ID获取的商品详细信息区别在于商品的可选参数不是全部,而是选中部分)
	 * @param parameter 参数
	 * @param commodityService 商品服务类
	 * @param username 用户名
	 * @return 商品集合
	 */
	List<OrderItemDto> getOrderCommodityByParameter(String parameter, CommodityService commodityService, String username);
	
	/**
	 * 插入订单项
	 * @param orderItem 订单项对象
	 * @return 是否成功
	 */
	boolean saveOrderItem(OrderItemDto orderItem);
	
	/**
	 * 根据订单号查询订单项
	 * @param orderNumber 订单号
	 * @param username 用户名
	 * @return 订单项集合
	 */
	List<OrderItemDto> getOrderItemByOrderNumber(Long orderNumber, String username);
	
	/**
	 * 更新订单项状态
	 * @param commodityId 商品ID
	 * @param orderId 订单ID
	 * @param orderItemStatus 订单状态
	 * @return 是否成功
	 */
	boolean updateOrderItemStatus(Long commodityId, Long orderId, OrderStatusEnum orderItemStatus);
}
