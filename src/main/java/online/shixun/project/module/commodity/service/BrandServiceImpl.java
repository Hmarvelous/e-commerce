package online.shixun.project.module.commodity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.shixun.project.module.commodity.dto.BrandDto;
import online.shixun.project.module.commodity.mapper.BrandMapper;

/**
 * 商品品牌服务接口实现类
 * @author am
 *
 */
@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandMapper brandMapper;
	
	/**
	 * 获取所有商品品牌信息
	 */
	@Override
	public List<BrandDto> getAllBrands() {
		return brandMapper.selectAllBrands();
	}

}
