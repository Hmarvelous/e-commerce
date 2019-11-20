package online.shixun.project.module.commodity.dto;

/**
 * 商品品牌实体类
 * @author am
 *
 */
public class BrandDto {

	// 品牌ID
	private Long id;
	
	// 品牌名称
	private String name;

	
	
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

	@Override
	public String toString() {
		return "BrandDto [id=" + id + ", name=" + name + "]";
	}
	
}
