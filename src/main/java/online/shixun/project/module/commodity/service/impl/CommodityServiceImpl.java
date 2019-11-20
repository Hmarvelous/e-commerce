package online.shixun.project.module.commodity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import online.shixun.project.module.commodity.dto.CommodityDto;
import online.shixun.project.module.commodity.mapper.CommodityMapper;
import online.shixun.project.module.commodity.service.CommodityService;

/**
 * 商品服务接口实现类
 * @author am
 *
 */
@Service
public class CommodityServiceImpl implements CommodityService {

	@Autowired
	private CommodityMapper commodityMapper;

	
	/**
	 * 查询五条热门商品基本信息
	 */
	@Override
	public List<CommodityDto> getHotFivesCommoditys() {
		return commodityMapper.selectHotFivesCommoditys();
	}

	/**
	 * 查询五条新品商品基本信息
	 */
	@Override
	public List<CommodityDto> getNewFivesCommoditys() {
		return commodityMapper.selectNewFivesCommoditys();
	}

	/**
	 * 查询五条精品商品基本信息
	 */
	@Override
	public List<CommodityDto> getBoutiqueFivesCommoditys() {
		return commodityMapper.selectBoutiqueFivesCommoditys();
	}

	/**
	 * 根据商品ID获取商品详细信息
	 */
	@Override
	public CommodityDto getDetailsCommodityById(Long id) {
		return commodityMapper.selectDetailsCommodityById(id);
	}

	/**
	 * 根据商品ID修改库存
	 */
	@Override
	public Integer updateCommodityStoreById(Long id, Integer store) {
		return commodityMapper.updateCommodityStoreById(id, store);
	}

	/**
	 * 根据关键字获取商品信息
	 */
	@Override
	public PageInfo<CommodityDto> getCommoditysByKeyword(String keyword, Integer page , Integer size, Long brandId) {
		PageHelper.startPage(page, size);
		List<CommodityDto> commoditys = commodityMapper.selectCommoditysByKeyword(keyword, brandId);
		PageInfo<CommodityDto> pageInfo = new PageInfo<CommodityDto>(commoditys);
		return pageInfo;
	}

	/**
	 *  根据商品分类ID获取商品信息
	 */
	@Override
	public List<CommodityDto> getAllCommodityBySortId(Long sortId, Long parentId) {
		return commodityMapper.selectAllCommodityBySortId(sortId, parentId);
	}

	/**
	 * 根据商品分类ID获取商品分页信息
	 */
	@Override
	public PageInfo<CommodityDto> getPageCommodityBySortId(Long sortId, Long parentId, Integer page, Integer size) {
		PageHelper.startPage(page, size);
		List<CommodityDto> commoditys = getAllCommodityBySortId(sortId, parentId);
		PageInfo<CommodityDto> pageInfo = new PageInfo<CommodityDto>(commoditys);
		return pageInfo;
	}

}
