package online.shixun.project.module.member.dto;


import com.alibaba.fastjson.JSONArray;

import online.shixun.project.module.commodity.dto.CommodityDto;

/**
 * 购物车实体类
 * @author am
 *
 */
public class CartItemDto {

	// 购物车ID
	private Long id;
	
	// 商品
	private CommodityDto commodity;
	
	// 所属会员
	private MemberDto member;
	
	// 商品数量
	private Integer count;
	
	// 选定参数JSON
	private String parameter;

	
	
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

	public MemberDto getMember() {
		return member;
	}

	public void setMember(MemberDto member) {
		this.member = member;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public JSONArray getParameter() {
		return JSONArray.parseArray(parameter);
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Override
	public String toString() {
		return "CartItemDto [id=" + id + ", commodity=" + commodity + ", member=" + member + ", count=" + count
				+ ", parameter=" + parameter + "]";
	}
	
}
