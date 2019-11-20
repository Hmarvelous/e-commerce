package online.shixun.project.module.commodity.service;

import online.shixun.project.module.commodity.dto.ParameterDto;

/**
 * 商品可选参数服务接口类
 * @author am
 *
 */
public interface ParameterService {

	/**
	 * 根据商品ID查询商品可选参数
	 * @param commodityId 商品ID
	 * @return 参数对象
	 */
	ParameterDto getParameterByCommodityId(Long commodityId);
}
