package online.shixun.project.module.order.enumerate;

/**
 * 订单状态枚举类
 * @author am
 *
 */
public enum OrderStatusEnum {

	/**
	 * 待付款
	 */
	PENDINGPAYMENT(0, "待付款"),
	/**
	 * 待发货
	 */
	PENDINGSHIP(1, "待发货"),
	/**
	 * 待收货
	 */
	PENDINGRECEIPT(2, "待收货"),
	/**
	 * 待评价
	 */
	PENDINGEVALUATION(3, "待评价"),
	/**
	 * 交易完毕
	 */
	COMPLETE(4, "交易完毕"),
	/**
	 * 回收站
	 */
	RECYCLE(5, "回收站"),
	/**
	 * 订单失效
	 */
	INVALID(6, "订单失效");
	
	private Integer ordinal;
	
	private String name;
	
	OrderStatusEnum(Integer ordinal, String name){
		this.ordinal=ordinal;
		this.name=name;
	}
	
	
	/**
	 * 根据编号获取名称
	 * @param ordinal 编号
	 * @return 存在返回对应的名称，不存在返回null
	 */
	public static String getName(int ordinal) {
		for (OrderStatusEnum e : OrderStatusEnum.values()) {
			if (e.getOrdinal() == ordinal) {
				return e.name;
			}
		}
		return null;
	}
	
	/**
	 * 根据编号获取对象
	 * @param ordinal 编号
	 * @return 存在返回对应的枚举对象，不存在返回null
	 */
	public static OrderStatusEnum get(int ordinal) {
		for (OrderStatusEnum e : OrderStatusEnum.values()) {
			if (e.getOrdinal() == ordinal) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * 根据名称获取对象
	 * @param name 名称
	 * @return 存在返回对应的枚举对象，不存在返回null
	 */
	public static OrderStatusEnum get(String name) {
		for (OrderStatusEnum e : OrderStatusEnum.values()) {
			if (e.getName().equals(name)) {
				return e;
			}
		}
		return null;
	}
 
	/**
	 * 根据名称获取编号
	 * @param name
	 * @return 存在返回编号，不存在返回-1
	 */
	public static int getOrdinals(String name) {
		for (OrderStatusEnum e : OrderStatusEnum.values()) {
			if (e.getName() == name) {
				return e.ordinal;
			}
		}
		return -1;
	}
	

	public Integer getOrdinal() {
		return ordinal;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
