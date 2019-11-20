package online.shixun.project.module.advertise.dto;

/**
 * 轮播广告实体类
 * @author am
 *
 */
public class CarouselAdDto {

	// 广告ID
	private Long id;
	
	// 广告图片
	private String image;
	
	// 是否显示广告
	private Boolean show;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	@Override
	public String toString() {
		return "CarouselAdDto [id=" + id + ", image=" + image + ", show=" + show + "]";
	}
	
}
