package online.shixun.project.module.order.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import online.shixun.project.module.member.dto.MemberDto;
import online.shixun.project.module.member.dto.ReceiverDto;
import online.shixun.project.module.order.enumerate.OrderStatusEnum;

/**
 * 订单实体类
 * @author am
 *
 */
public class OrderDto {

	// 订单ID
	private Long id;
	
	// 实付金额
	private Double totalAmount;
	
	// 商品总数
	private Integer totalCommodity;
	
	// 订单状态
	private OrderStatusEnum orderStatus;
	
	// 商品实际总价
	private Double actualAmount;
	
	// 下单会员
	private MemberDto member;
	
	// 收货地址
	private ReceiverDto receiver;
		
	// 订单号
	private Long orderNumber;
	
	// 创建时间
	private Date createTime;
	
	// 最后更新时间
	private Date updateTime;

	// 买家留言
	private String comments;
	
	// 订单包含的订单项
	private List<OrderItemDto> orderItems;
	
	
	
	public OrderDto() { }

	public OrderDto(Double totalAmount, Integer totalCommodity, OrderStatusEnum orderStatus, Double actualAmount,
			MemberDto member, ReceiverDto receiver, Long orderNumber, Date createTime, Date updateTime, String comments) {
		this.totalAmount = totalAmount;
		this.totalCommodity = totalCommodity;
		this.orderStatus = orderStatus;
		this.actualAmount = actualAmount;
		this.member = member;
		this.receiver = receiver;
		this.orderNumber = orderNumber;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getTotalCommodity() {
		return totalCommodity;
	}

	public void setTotalCommodity(Integer totalCommodity) {
		this.totalCommodity = totalCommodity;
	}

	public OrderStatusEnum getOrderStatus() {
		return orderStatus;
	}
	
	public String getOrderStatusString() {
		return orderStatus.getName();
	}

	public void setOrderStatus(OrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public MemberDto getMember() {
		return member;
	}

	public void setMember(MemberDto member) {
		this.member = member;
	}

	public ReceiverDto getReceiver() {
		return receiver;
	}

	public void setReceiver(ReceiverDto receiver) {
		this.receiver = receiver;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}
	
	public String getOrderNumberString() {
		return orderNumber.toString();
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getCreateTime() {
		return createTime;
	}
	
	public String getCreateTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(createTime);
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	
	public String getUpdateTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(updateTime);
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<OrderItemDto> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDto> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public String toString() {
		return "OrderDto [id=" + id + ", totalAmount=" + totalAmount + ", totalCommodity=" + totalCommodity
				+ ", orderStatus=" + orderStatus + ", actualAmount=" + actualAmount + ", member=" + member
				+ ", receiver=" + receiver + ", orderNumber=" + orderNumber + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", comments=" + comments + ", orderItems=" + orderItems + "]";
	}

}
