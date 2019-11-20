package online.shixun.project.module.commodity.service;

import java.util.List;

import online.shixun.project.module.commodity.dto.BrandDto;

/**
 * 商品品牌服务接口类
 * @author am
 *
 */
public interface BrandService {

	/**
	 * 获取所有商品品牌信息
	 * @return 商品品牌集合
	 */
	List<BrandDto> getAllBrands();
}
