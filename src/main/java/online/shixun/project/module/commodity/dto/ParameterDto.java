package online.shixun.project.module.commodity.dto;

import com.alibaba.fastjson.JSONArray;

/**
 * 商品可选参数实体类
 * @author am
 *
 */
public class ParameterDto {

	// 可选参数ID(商品ID)
	private Long id;
	
	// 商品参数JSON数据
	private String parameterJson;

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JSONArray getParameterJson() {
		return JSONArray.parseArray(parameterJson);
	}
	
	public void setParameterJson(String parameterJson) {
		this.parameterJson = parameterJson;
	}


	@Override
	public String toString() {
		return "ParameterDto [id=" + id + ", parameterJson=" + parameterJson + "]";
	}
	
}
