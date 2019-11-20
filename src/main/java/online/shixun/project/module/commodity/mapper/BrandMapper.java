package online.shixun.project.module.commodity.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import online.shixun.project.module.commodity.dto.BrandDto;

/**
 * 
 * @author am
 *
 */
@Mapper
public interface BrandMapper {

	/**
	 * 获取所有商品品牌信息
	 * @return 商品品牌集合
	 */
	List<BrandDto> selectAllBrands();
}
