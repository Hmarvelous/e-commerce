package online.shixun.project.module.commodity.dto;

import java.util.List;

/**
 * 商品分类实体类
 * @author am
 *
 */
public class SortDto {

	// 分类ID
	private Long id;
	
	// 分类名称
	private String name;
	
	// 子分类(三层关系)
	private List<SortDto> parentSorts;
	
	// 分类图片
	private String image;

	
	
	public SortDto() { }

	public SortDto(String name, List<SortDto> parentSorts, String image) {
		this.name = name;
		this.parentSorts = parentSorts;
		this.image = image;
	}

	public SortDto(Long id, String name, List<SortDto> parentSorts, String image) {
		this.id = id;
		this.name = name;
		this.parentSorts = parentSorts;
		this.image = image;
	}

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

	public List<SortDto> getParentSorts() {
		return parentSorts;
	}

	public void setParentSorts(List<SortDto> parentSorts) {
		this.parentSorts = parentSorts;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "SortDto [id=" + id + ", name=" + name + ", parentSorts=" + parentSorts + ", image=" + image + "]";
	}
}
