package online.shixun.project.module.commodity.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import online.shixun.project.module.commodity.dto.ParameterDto;

/**
 * 
 * @author am
 *
 */
@Mapper
public interface ParameterMapper {

	/**
	 * 根据商品ID查询商品可选参数
	 * @param commodityId 商品ID
	 * @return 参数对象
	 */
	ParameterDto selectParameterByCommodityId(@Param("commodityId") Long commodityId);
	
}
