package online.shixun.project.module.spike.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 秒杀场次实体类
 * @ClassName: SpikeSessionDto.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: am
 * @date: 2019年10月26日 下午3:41:18
 */
public class SpikeSessionDto implements Serializable {

	private static final long serialVersionUID = -6439082098655158537L;

	/**
	 * 场次ID
	 */
	private Long id;
	
	/**
	 * 场次名称
	 */
	private String name;
	
	/**
	 * 场次描述
	 */
	private String description;
	
	/**
	 * 是否有效
	 */
	private Boolean dataFlag;

	/**
	 * 该活动场次的全部有效商品
	 */
	private List<SpikeCommodityDto> spikeCommoditys;
	
	
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(Boolean dataFlag) {
		this.dataFlag = dataFlag;
	}

	public List<SpikeCommodityDto> getSpikeCommoditys() {
		return spikeCommoditys;
	}

	public void setSpikeCommoditys(List<SpikeCommodityDto> spikeCommoditys) {
		this.spikeCommoditys = spikeCommoditys;
	}

	@Override
	public String toString() {
		return "SpikeSessionDto [id=" + id + ", name=" + name + ", description=" + description + ", dataFlag="
				+ dataFlag + ", spikeCommoditys=" + spikeCommoditys + "]";
	}
	
	
}
