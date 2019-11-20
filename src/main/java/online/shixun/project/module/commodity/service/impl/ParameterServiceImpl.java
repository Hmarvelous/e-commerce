package online.shixun.project.module.commodity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.shixun.project.module.commodity.dto.ParameterDto;
import online.shixun.project.module.commodity.mapper.ParameterMapper;
import online.shixun.project.module.commodity.service.ParameterService;

/**
 * 商品可选参数服务实现类
 * @author am
 *
 */
@Service
public class ParameterServiceImpl implements ParameterService {

	@Autowired
	private ParameterMapper parameterMapper;
	
	/**
	 * 根据商品ID查询商品可选参数
	 */
	@Override
	public ParameterDto getParameterByCommodityId(Long commodityId) {
		return parameterMapper.selectParameterByCommodityId(commodityId);
	}
	
}
