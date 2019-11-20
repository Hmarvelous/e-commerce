package online.shixun.project.module.member.dto;

/**
 * 收货地址实体类
 * @author am
 *
 */
public class ReceiverDto {

	// 收货地址ID
	private Long id;
	
	// 收货人姓名
	private String name;
	
	// 区域地址
	private String[] areaAddress;
	
	// 详细地址
	private String detailedAddress;
	
	// 电话号码
	private String phone;
	
	// 邮政编码
	private String zipCode;
	
	// 所属用户
	private MemberDto member;

	// 是否为默认收货地址
	private Boolean defaultAddress;
	
	
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

	public String[] getAreaAddress() {
		return areaAddress;
	}

	public void setAreaAddress(String areaAddress) {
		this.areaAddress = areaAddress.split("/");
	}

	public String getDetailedAddress() {
		return detailedAddress;
	}

	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public MemberDto getMember() {
		return member;
	}

	public void setMember(MemberDto member) {
		this.member = member;
	}

	
	public Boolean getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(Boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	@Override
	public String toString() {
		return "ReceiverDto [id=" + id + ", name=" + name + ", areaAddress=" + areaAddress + ", detailedAddress="
				+ detailedAddress + ", phone=" + phone + ", zipCode=" + zipCode + ", member=" + member
				+ ", defaultAddress=" + defaultAddress + "]";
	}
	
}
