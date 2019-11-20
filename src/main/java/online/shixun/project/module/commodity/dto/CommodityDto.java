package online.shixun.project.module.commodity.dto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品实体类
 * @author am
 *
 */
public class CommodityDto {

	// 商品ID
	private Long id;
		
	// 商品名称
	private String name;
	
	// 商品价格
	private Double price;
	
	// 市场价格
	private Double marketPrice;
	
	// 商品库存
	private Integer store;
	
	// 商品被购买量
	private Integer purchase;
	
	// 商品点击量
	private Long click;
	
	// 积分
	private Integer point;
	
	// 是否上架
	private Boolean putShelves;
	
	// 是否为精品
	private Boolean boutique;
	
	// 是否为新品
	private Boolean newProduct;
	
	// 是否为热销商品
	private Boolean hot;
	
	// 商品描述
	private String description;
	
	// 商品首页图片名称
	private String homePicture;
	
	// 商品所属分类
	private SortDto sort;
	
	// 商品所属品牌
//	private BrandDto brand;
	
	// 商品购买可选参数
	private ParameterDto parameters;
	
	// 商品截图组(逗号隔开)
	private String screenshots;
	
	// 商品上架日期
	private Date putShelvesData;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Double getMarketPrice() {
		return marketPrice;
	}
	
	public String getMarketPriceString() {
		if (marketPrice != null) {
			DecimalFormat df = new DecimalFormat("#.00");
			return df.format(marketPrice);
		}
		return "";
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	public Integer getPurchase() {
		return purchase;
	}

	public void setPurchase(Integer purchase) {
		this.purchase = purchase;
	}

	public Long getClick() {
		return click;
	}

	public void setClick(Long click) {
		this.click = click;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Boolean getPutShelves() {
		return putShelves;
	}

	public void setPutShelves(Boolean putShelves) {
		this.putShelves = putShelves;
	}

	public Boolean getBoutique() {
		return boutique;
	}

	public void setBoutique(Boolean boutique) {
		this.boutique = boutique;
	}

	public Boolean getNewProduct() {
		return newProduct;
	}

	public void setNewProduct(Boolean newProduct) {
		this.newProduct = newProduct;
	}

	public Boolean getHot() {
		return hot;
	}

	public void setHot(Boolean hot) {
		this.hot = hot;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHomePicture() {
		return homePicture;
	}

	public void setHomePicture(String homePicture) {
		this.homePicture = homePicture;
	}

	public SortDto getSort() {
		return sort;
	}

	public void setSort(SortDto sort) {
		this.sort = sort;
	}
	
	public ParameterDto getParameters() {
		return parameters;
	}

	public void setParameters(ParameterDto parameters) {
		this.parameters = parameters;
	}

	public String getScreenshots() {
		return screenshots;
	}
	
	/**
	 * 获取商品截图数组
	 * @return
	 */
	public List<String> getScreenshotList() {
		if (screenshots != null) {
			List<String> screenshotList = new ArrayList<String>();
			String[] screenshot = screenshots.split(",");
			for (String s : screenshot) {
				screenshotList.add(s);
			}
			return screenshotList;
		}
		return new ArrayList<String>();
	}

	public void setScreenshots(String screenshots) {
		this.screenshots = screenshots;
	}

	public Date getPutShelvesData() {
		return putShelvesData;
	}

	public void setPutShelvesData(Date putShelvesData) {
		this.putShelvesData = putShelvesData;
	}

	@Override
	public String toString() {
		return "CommodityDto [id=" + id + ", name=" + name + ", price=" + price + ", marketPrice=" + marketPrice
				+ ", store=" + store + ", purchase=" + purchase + ", click=" + click + ", point=" + point
				+ ", putShelves=" + putShelves + ", boutique=" + boutique + ", newProduct=" + newProduct + ", hot="
				+ hot + ", description=" + description + ", homePicture=" + homePicture + ", sort=" + sort
				+ ", parameters=" + parameters + ", screenshots=" + screenshots + ", putShelvesData=" + putShelvesData
				+ "]";
	}
	
}
