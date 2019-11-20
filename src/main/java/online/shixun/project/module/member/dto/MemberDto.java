package online.shixun.project.module.member.dto;

/**
 * 会员实体类
 * @author am
 *
 */
public class MemberDto {

	// 会员ID
	private Long id;
	
	// 会员名
	private String username;
	
	// 密码
	private String password;
	
	// 电子邮箱
	private String email;

	// 余额
	private Double balance;
	
	// 购物积分
	private Integer point;
	
	// 会员等级
	
	
	// 是否被冻结
	private Boolean freeze;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Boolean getFreeze() {
		return freeze;
	}

	public void setFreeze(Boolean freeze) {
		this.freeze = freeze;
	}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", balance=" + balance + ", point=" + point + ", freeze=" + freeze + "]";
	}
}
