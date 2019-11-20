package online.shixun.project.module.spike.dto;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import online.shixun.project.module.commodity.dto.CommodityDto;

/**
 * 秒杀商品实体类
 * @ClassName: SpikeCommodityDto.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: am
 * @date: 2019年10月26日 下午3:44:59
 */
public class SpikeCommodityDto implements Serializable {

	private static final long serialVersionUID = -5121062837071019773L;

	/**
	 * 商品ID
	 */
	private Long id;
	
	/**
	 * 商品价格
	 */
	private Double price;
	
	/**
	 * 商品库存
	 */
	private Integer store;
	
	/**
	 * 商品描述
	 */
	private String description;
	
	/**
	 * 商品活动结束时间
	 */
	private Date startTime;
	
	/**
	 * 商品所属活动场次
	 */
	private SpikeSessionDto spikeSession;
	
	/**
	 * 商品是否有效
	 */
	private Boolean dataFlag;

	/**
	 * 商品对象
	 */
	private CommodityDto commodity;

	/**
	 * 活动状态
	 */
	private Integer activeStatus;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public String getPriceString() { 
		if (price != null) {
			DecimalFormat df = new DecimalFormat("#.00");
			return df.format(price);
		}
		return "";
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartTime() {
		return startTime;
	}

	public String getStartTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(startTime);
	}
	
	public String getStartTimeIntegerString() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(startTime);
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public SpikeSessionDto getSpikeSession() {
		return spikeSession;
	}

	public void setSpikeSession(SpikeSessionDto spikeSession) {
		this.spikeSession = spikeSession;
	}

	public Boolean getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(Boolean dataFlag) {
		this.dataFlag = dataFlag;
	}

	public CommodityDto getCommodity() {
		return commodity;
	}

	public void setCommodity(CommodityDto commodity) {
		this.commodity = commodity;
	}

	public Integer getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}

	@Override
	public String toString() {
		return "SpikeCommodityDto [id=" + id + ", price=" + price + ", store=" + store + ", description=" + description
				+ ", startTime=" + startTime + ", spikeSession=" + spikeSession + ", dataFlag=" + dataFlag
				+ ", commodity=" + commodity + ", activeStatus=" + activeStatus + "]";
	}

}
