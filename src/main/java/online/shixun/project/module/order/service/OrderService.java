package online.shixun.project.module.order.service;

import java.util.Date;

import com.github.pagehelper.PageInfo;

import online.shixun.project.module.member.dto.MemberDto;
import online.shixun.project.module.member.dto.ReceiverDto;
import online.shixun.project.module.order.dto.OrderDto;
import online.shixun.project.module.order.enumerate.OrderStatusEnum;

/**
 * 订单服务接口类
 * @author am
 *
 */
public interface OrderService {

	/**
	 * 生成订单
	 * @param parameter 参数
	 * @param member 会员对象
	 * @param receiver 收货地址对象
	 * @return 订单对象
	 */
	OrderDto generateOrders(String parameter, MemberDto member, ReceiverDto receiver);
	
	/**
	 * 通过订单号查询订单信息
	 * @param orderNumber 订单号
	 * @param username 用户名
	 * @return 查询到的订单对象
	 */
	OrderDto getOrderByOrderNumber(Long orderNumber, String username);
	
	/**
	 * 根据订单状态获取订单数量
	 * @param orderStatus 订单状态
	 * @param username 用户名
	 * @return 订单数量
	 */
	Integer getOrderCountByOrderStatus(OrderStatusEnum orderStatus, String username);

	/**
	 * 根据订单状态查询分页订单信息
	 * @param page 获取的页码
	 * @param size 页码数据大小
	 * @param orderStatus 订单状态
	 * @param username 用户名
	 * @return 分页集合
	 */
	PageInfo<OrderDto> getOrderByOrderStatus(Integer page, Integer size, OrderStatusEnum orderStatus, String username);
	
	/**
	 * 订单付款
	 * @param orderNumber 订单号
	 * @param member 会员对象
	 * @return 是否付款成功
	 */
	boolean orderPayment(Long orderNumber, MemberDto member);
	
	/**
	 * 根据订单ID获取订单信息
	 * @param id 订单ID
	 * @param username 用户名
	 * @return 订单对象
	 */
	OrderDto getOrderById(Long id, String username);
	
	/**
	 * 更新订单状态
	 * @param orderNumber 订单号
	 * @param orderStatus 订单状态
	 * @param memberId 用户ID
	 * @return 是否更新成功
	 */
	boolean updateOrderStatus(Long orderNumber, OrderStatusEnum orderStatus, Long memberId);
	
	/**
	 * 收货
	 * @param orderNumber 订单号
	 * @param member 会员对象
	 * @return 收货是否成功
	 */
	boolean orderReceipt(Long orderNumber, MemberDto member);
	
	/**
	 * 根据订单号添加订单至回收站
	 * @param orderNumber 订单号
	 * @param member 会员对象
	 * @return 是否添加至回收站
	 */
	boolean toRecycleBinOrderByOrderNumber(Long orderNumber, MemberDto member);
	
	/**
	 * 根据订单号更新订单最后更新时间
	 * @param orderNumber 订单号
	 * @param date 更新时间
	 * @param username 用户名
	 * @param iden 更新标识  0为正常更新,1为订单催促更新
	 * @return 更新是否成功
	 */
	boolean updateOrderUpdateTime(Long orderNumber, Date date, String username, int iden);
}
