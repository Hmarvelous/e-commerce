package online.shixun.project.module.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import online.shixun.project.module.order.dto.OrderDto;
import online.shixun.project.module.order.enumerate.OrderStatusEnum;

/**
 * 
 * @author am
 *
 */
@Mapper
public interface OrderMapper {

	/**
	 * 添加订单
	 * @param order 订单对象
	 * @return 返回1为成功  返回0为失败
	 */
	Integer insertOrder(@Param("order") OrderDto order);
	
	/**
	 * 通过订单号查询订单信息
	 * @param orderNumber 订单号
	 * @param username 用户名
	 * @return 查询到的订单对象
	 */
	OrderDto selectOrderByOrderNumber(@Param("orderNumber") Long orderNumber, @Param("username") String username);
	
	/**
	 * 根据订单状态获取订单数量
	 * @param orderStatus 订单状态
	 * @param username 用户名
	 * @return 订单数量
	 */
	Integer selectOrderCountByOrderStatus(@Param("orderStatus") OrderStatusEnum orderStatus, @Param("username") String username);
	
	/**
	 * 根据订单状态获取全部订单
	 * @param orderStatus 订单状态
	 * @param username 用户名
	 * @return 订单集合
	 */
	List<OrderDto> selectAllOrderByOrderStatus(@Param("orderStatus") OrderStatusEnum orderStatus, @Param("username") String username);
	
	/**
	 * 更新订单状态
	 * @param orderNumber 订单号
	 * @param orderStatus 订单状态
	 * @param memberId 用户ID
	 * @return 返回1为成功  返回0为失败
	 */
	Integer updateOrderStatus(@Param("orderNumber") Long orderNumber, @Param("orderStatus") OrderStatusEnum orderStatus, @Param("memberId") Long memberId);

	/**
	 * 根据订单ID获取订单信息
	 * @param id 订单ID
	 * @param username 用户名
	 * @return 订单对象
	 */
	OrderDto selectOrderById(@Param("id") Long id, @Param("username") String username);
	
	/**
	 * 根据订单号更新订单最后更新时间
	 * @param orderNumber 订单号
	 * @param time 更新时间
	 * @return 返回1为更新成功  返回0为更新失败
	 */
	int updateOrderUpdateTime(@Param("orderNumber") Long orderNumber, @Param("time") String time);
}
