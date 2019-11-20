package online.shixun.project.module.commodity.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import online.shixun.project.module.commodity.dto.CommodityDto;

/**
 * 商品服务接口类
 * @author am
 *
 */
public interface CommodityService {

	/**
	 *查询五条热门商品基本信息
	 * @return 商品集合
	 */
	List<CommodityDto> getHotFivesCommoditys();
	
	/**
	 * 查询五条新品商品基本信息
	 * @return 商品集合
	 */
	List<CommodityDto> getNewFivesCommoditys();
	
	/**
	 * 查询五条精品商品基本信息
	 * @return 商品集合
	 */
	List<CommodityDto> getBoutiqueFivesCommoditys();
	
	/**
	 * 根据商品ID获取商品详细信息
	 * @param id 商品ID
	 * @return 商品对象
	 */
	CommodityDto getDetailsCommodityById(Long id);
	
	/**
	 * 根据商品ID修改库存
	 * @param id 商品ID
	 * @param store 商品库存
	 * @return 返回1为修改成功  返回0为修改失败
	 */
	Integer updateCommodityStoreById(Long id, Integer store);
	
	/**
	 *  根据关键字获取商品信息
	 * @param keyword 关键字
	 * @param page 获取的页码
	 * @param size 页码数据大小
	 * @param brandId 商品品牌ID
	 * @return 分页数据
	 */
	PageInfo<CommodityDto> getCommoditysByKeyword(String keyword, Integer page , Integer size, Long brandId);
	
	/**
	 * 根据商品分类ID获取商品信息
	 * @param sortId 商品分类ID
	 * @param parentId 商品分类ID的父级ID
	 * @return 商品集合
	 */
	List<CommodityDto> getAllCommodityBySortId(Long sortId, Long parentId);
	
	/**
	 * 根据商品分类ID获取商品分页信息
	 * @param sortId 商品分类ID
	 * @param parentId 商品分类ID的父级ID
	 * @param page 需要获取的页码
	 * @param size 每页数据大小
	 * @return 分页对象
	 */
	PageInfo<CommodityDto> getPageCommodityBySortId(Long sortId, Long parentId, Integer page, Integer size);
}
