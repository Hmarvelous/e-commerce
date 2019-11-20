package online.shixun.project.module.order.dto;

import java.text.DecimalFormat;

import com.alibaba.fastjson.JSONArray;

import online.shixun.project.module.commodity.dto.CommodityDto;
import online.shixun.project.module.order.enumerate.OrderStatusEnum;

/**
 * 订单项实体类
 * @author am
 *
 */
public class OrderItemDto {
	
	// 订单项ID
	private Long id;
	
	// 购买的商品
	private CommodityDto commodity;
	
	// 商品单价
	private Double unitPrice;
	
	// 成交数量
	private Integer count;
	
	// 实付金额
	private Double actualAmount;

	// 购买时选择的参数(JSON)
	private String parameter;

	// 所属订单对象
	private OrderDto order;
	
	// 订单项状态
	private OrderStatusEnum orderItemStatus;
	
	// 优惠方式
	private Integer preferentialWay;
	
	
	
	public OrderItemDto() { }

	public OrderItemDto(CommodityDto commodity, Double unitPrice, Integer count, Double actualAmount, String parameter,
			OrderStatusEnum orderItemStatus, OrderDto order) {
		this.commodity = commodity;
		this.unitPrice = unitPrice;
		this.count = count;
		this.actualAmount = actualAmount;
		this.parameter = parameter;
		this.orderItemStatus = orderItemStatus;
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CommodityDto getCommodity() {
		return commodity;
	}

	public void setCommodity(CommodityDto commodity) {
		this.commodity = commodity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}
	
	public String getUnitPriceString() { 
		if (unitPrice != null) {
			DecimalFormat df = new DecimalFormat("#.00");
			return df.format(unitPrice);
		}
		return "";
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getActualAmount() {
		return actualAmount;
	}
	
	public String getActualAmountString() { 
		if (actualAmount != null) {
			DecimalFormat df = new DecimalFormat("#.00");
			return df.format(actualAmount);
		}
		return "";
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getParameter() {
		return parameter;
	}
	
	public JSONArray getParameterJSONArray() {
		return JSONArray.parseArray(parameter);
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public OrderDto getOrder() {
		return order;
	}

	public void setOrder(OrderDto order) {
		this.order = order;
	}

	public OrderStatusEnum getOrderItemStatus() {
		return orderItemStatus;
	}
	
	public String getOrderItemStatusString() {
		return orderItemStatus.getName();
	}

	public void setOrderItemStatus(OrderStatusEnum orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
	}

	public Integer getPreferentialWay() {
		return preferentialWay;
	}

	public void setPreferentialWay(Integer preferentialWay) {
		this.preferentialWay = preferentialWay;
	}

	@Override
	public String toString() {
		return "OrderItemDto [id=" + id + ", commodity=" + commodity + ", unitPrice=" + unitPrice + ", count=" + count
				+ ", actualAmount=" + actualAmount + ", parameter=" + parameter + ", order=" + order
				+ ", orderItemStatus=" + orderItemStatus + ", preferentialWay=" + preferentialWay + "]";
	}

}
