package online.shixun.project.module.order.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import online.shixun.project.module.commodity.dto.CommodityDto;
import online.shixun.project.module.commodity.service.CommodityService;
import online.shixun.project.module.member.dto.MemberDto;
import online.shixun.project.module.member.dto.ReceiverDto;
import online.shixun.project.module.member.service.MemberService;
import online.shixun.project.module.order.dto.OrderDto;
import online.shixun.project.module.order.dto.OrderItemDto;
import online.shixun.project.module.order.enumerate.OrderStatusEnum;
import online.shixun.project.module.order.mapper.OrderMapper;
import online.shixun.project.module.order.service.OrderItemService;
import online.shixun.project.module.order.service.OrderService;
import online.shixun.project.module.spike.dto.SpikeCommodityDto;
import online.shixun.project.module.spike.service.SpikeCommodityService;

/**
 * 订单服务接口实现类
 * @author am
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private SpikeCommodityService spikeCommodityService;
	
	/**
	 * 生成订单
	 */
	@Override
	@Transactional
	public OrderDto generateOrders(String parameter, MemberDto member, ReceiverDto receiver) {
		JSONObject json = JSONObject.parseObject(parameter);
		
		// 判断订单是否可以提交
		long orderNumberJson = json.getLongValue("orderNumber");
		// 通过订单号查询订单信息
		OrderDto order_ = getOrderByOrderNumber(orderNumberJson, member.getUsername());
		if (order_ != null) {
			return order_;
		}

		double totalAmount = 0;
		double actualAmount = 0;
		int totalCommodity = 0;
		// 查询商品集合(计算金额,数量)
		List<OrderItemDto> orderItems = new ArrayList<OrderItemDto>();
		for (Object j : json.getJSONArray("commodity")) {
			JSONObject jo = (JSONObject) j;
			CommodityDto commodity = commodityService.getDetailsCommodityById(jo.getLong("id"));
			totalCommodity += jo.getIntValue("count");
			// 计算参数溢价
			double premium = 0;
			for (Object o : commodity.getParameters().getParameterJson()) {
				for (Object ox : ((JSONObject) o).getJSONArray("parameters")) {
					JSONObject jox = (JSONObject) ox;
					for (Object parameterObject : jo.getJSONArray("parameter")) {
						if (jox.get("name").equals(((JSONObject) parameterObject).get("name"))) {
							premium += jox.getDoubleValue("price");
						}
					}
				}
			}
			// 商品实际总价
			actualAmount += (commodity.getPrice() + premium) * jo.getIntValue("count");
			// 计算总价
			if (json.containsKey("preferentialWay")) {
				if (json.getInteger("preferentialWay") == 1) {
					// 根据商品ID查询秒杀商品信息
					SpikeCommodityDto spikeCommodity = spikeCommodityService.getSpikeCommodityById(jo.getLongValue("id"));
					commodity.setPrice(spikeCommodity.getPrice());
				}
			} else {
				commodity.setPrice(commodity.getPrice() + premium);
			}
			// 实付金额
			totalAmount += commodity.getPrice() * jo.getIntValue("count");
			// 构造订单项
			OrderItemDto orderItem = new OrderItemDto(commodity,
					                                  commodity.getPrice(),
					                                  jo.getIntValue("count"), 
					                                  commodity.getPrice() * jo.getIntValue("count"), 
					                                  jo.get("parameter").toString(),
					                                  OrderStatusEnum.PENDINGPAYMENT,
					                                  null);
			orderItems.add(orderItem);
		}
		// 生成订单号
		long orderNumber = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + (int)((Math.random()*9+1)*1000));
		// 写入订单
		OrderDto order = new OrderDto(totalAmount,
									  totalCommodity,
									  OrderStatusEnum.PENDINGPAYMENT,
									  actualAmount,
									  member,
									  receiver,
									  orderNumber,
									  new Date(),
									  new Date(),
									  json.get("comments").toString());
		orderMapper.insertOrder(order);
		
		// 写入订单项
		for (OrderItemDto orderItem : orderItems) {
			orderItem.setOrder(order);
			if (json.getInteger("preferentialWay") == null) {
				orderItem.setPreferentialWay(0);
			} else {
				orderItem.setPreferentialWay(json.getInteger("preferentialWay"));
			}
			orderItemService.saveOrderItem(orderItem);
		}
		return order;
	}

	/**
	 * 通过订单号查询订单信息
	 */
	@Override
	public OrderDto getOrderByOrderNumber(Long orderNumber, String username) {
		return orderMapper.selectOrderByOrderNumber(orderNumber, username);
	}

	/**
	 * 根据订单状态获取订单数量
	 */
	@Override
	public Integer getOrderCountByOrderStatus(OrderStatusEnum orderStatus, String username) {
		return orderMapper.selectOrderCountByOrderStatus(orderStatus, username);
	}

	/**
	 *  根据订单状态查询分页订单信息
	 */
	@Override
	public PageInfo<OrderDto> getOrderByOrderStatus(Integer page, Integer size, OrderStatusEnum orderStatus,
			String username) {
		PageHelper.startPage(page, size);
		List<OrderDto> orders = orderMapper.selectAllOrderByOrderStatus(orderStatus, username);
		PageInfo<OrderDto> pageInfo = new PageInfo<OrderDto>(orders);
		return pageInfo;
	}

	/**
	 * 订单付款
	 */
	@Override
	@Transactional
	public boolean orderPayment(Long orderNumber, MemberDto member) {
		// 查询订单信息
		OrderDto order = orderMapper.selectOrderByOrderNumber(orderNumber, member.getUsername());
		if (order == null) {
			return false;
		}
		// 计算积分
		int point = 0;
		// 查询订单的订单项信息
		List<OrderItemDto> orderItems = orderItemService.getOrderItemByOrderNumber(orderNumber, member.getUsername());
		for (OrderItemDto orderItem : orderItems) {
			if (orderItem.getPreferentialWay() >= 1) {
				// 优惠商品不加积分
				continue;
			}
			point += orderItem.getCommodity().getPoint();
		}
		// 扣钱,加积分
		member.setBalance(member.getBalance() - order.getTotalAmount());
		member.setPoint(member.getPoint() + point);
		memberService.updateMember(member);
		// 扣库存
		for (OrderItemDto orderItem : orderItems) {
			if (orderItem.getPreferentialWay() >= 1) {
				// 优惠商品不减库存
				continue;
			}
			commodityService.updateCommodityStoreById(orderItem.getCommodity().getId(), orderItem.getCommodity().getStore() - orderItem.getCount());
		}
		// 更新订单项状态
		for (OrderItemDto orderItem : orderItems) {
			orderItemService.updateOrderItemStatus(orderItem.getCommodity().getId(), order.getId(), OrderStatusEnum.PENDINGSHIP);
		}
		// 更新订单状态
		orderMapper.updateOrderStatus(orderNumber, OrderStatusEnum.PENDINGSHIP, member.getId());
		return true;
	}

	/**
	 * 根据订单ID获取订单信息
	 */
	@Override
	public OrderDto getOrderById(Long id, String username) {
		return orderMapper.selectOrderById(id, username);
	}

	/**
	 * 更新订单状态
	 */
	@Override
	public boolean updateOrderStatus(Long orderNumber, OrderStatusEnum orderStatus, Long memberId) {
		return orderMapper.updateOrderStatus(orderNumber, orderStatus, memberId) == 1;
	}

	/**
	 * 收货
	 */
	@Override
	@Transactional
	public boolean orderReceipt(Long orderNumber, MemberDto member) {
		// 根据订单号获取订单
		OrderDto order = getOrderByOrderNumber(orderNumber, member.getUsername());
		// 根据订单号查询订单项
		List<OrderItemDto> orderItems = orderItemService.getOrderItemByOrderNumber(orderNumber, member.getUsername());
		// 修改订单项状态
		for (OrderItemDto orderItem : orderItems) {
			orderItemService.updateOrderItemStatus(orderItem.getCommodity().getId(), order.getId(), OrderStatusEnum.PENDINGEVALUATION);
		}
		// 修改订单状态
		updateOrderStatus(orderNumber, OrderStatusEnum.PENDINGEVALUATION, member.getId());
		return true;
	}

	/**
	 * 根据订单ID添加订单至回收站
	 */
	@Override
	@Transactional
	public boolean toRecycleBinOrderByOrderNumber(Long orderNumber, MemberDto member) {
		// 通过订单号获取订单信息
		OrderDto order = getOrderByOrderNumber(orderNumber, member.getUsername());
		// 根据订单号获取订单项
		List<OrderItemDto> orderItems = orderItemService.getOrderItemByOrderNumber(orderNumber, member.getUsername());
		// 更新订单项状态
		for (OrderItemDto orderItem : orderItems) {
			orderItemService.updateOrderItemStatus(orderItem.getCommodity().getId(), order.getId(), OrderStatusEnum.RECYCLE);
		}
		// 更新订单状态
		updateOrderStatus(orderNumber, OrderStatusEnum.RECYCLE, member.getId());
		return true;
	}

	/**
	 * 根据订单号更新订单最后更新时间
	 */
	@Override
	@Transactional
	public boolean updateOrderUpdateTime(Long orderNumber, Date date, String username, int iden) {
		
		if (iden == 0) {
			if (orderMapper.updateOrderUpdateTime(orderNumber, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)) == 1) {
				// 更新成功
				return true;
			}
			
		} else if (iden == 1) {
			// 订单催促
			// 判断上次修改时间是否超过一天
			OrderDto order = orderMapper.selectOrderByOrderNumber(orderNumber, username);
			if ((date.getTime() - order.getUpdateTime().getTime()) > 24*60*60*1000) {
				// 大于一天,可以更新
				if (orderMapper.updateOrderUpdateTime(orderNumber, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)) == 1) {
					//更新成功
					return true;
				}
			} else {
				// 小于一天,无法更新
				return false;
			}
		}
		return false;
	}


}
