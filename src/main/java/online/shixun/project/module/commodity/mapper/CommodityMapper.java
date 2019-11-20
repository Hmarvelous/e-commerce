package online.shixun.project.module.commodity.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import online.shixun.project.module.commodity.dto.CommodityDto;

/**
 * 
 * @author am
 *
 */
@Mapper
public interface CommodityMapper {

	/**
	 *查询五条热门商品基本信息
	 * @return 商品集合
	 */
	List<CommodityDto> selectHotFivesCommoditys();
	
	/**
	 * 查询五条新品商品基本信息
	 * @return
	 */
	List<CommodityDto> selectNewFivesCommoditys();
	
	/**
	 * 查询五条精品商品基本信息
	 * @return
	 */
	List<CommodityDto> selectBoutiqueFivesCommoditys();
	
	
	/**
	 * 根据商品ID获取商品详细信息
	 * @param id
	 * @return
	 */
	CommodityDto selectDetailsCommodityById(@Param("id") Long id);
	
	/**
	 * 根据商品ID修改库存
	 * @param id 商品ID
	 * @param store 商品库存
	 * @return 返回1为修改成功  返回0为修改失败
	 */
	Integer updateCommodityStoreById(@Param("id") Long id, @Param("store") Integer store);
	
	/**
	 * 根据关键字获取商品信息
	 * @param keyword 关键字
	 * @param brandId 商品品牌ID
	 * @return 商品集合
	 */
	List<CommodityDto> selectCommoditysByKeyword(@Param("keyword") String keyword, @Param("brandId") Long brandId);
	
	/**
	 * 根据商品分类ID获取商品信息
	 * @param sortId 商品分类ID
	 * @param parentId 商品分类ID的父级ID
	 * @return 商品集合
	 */
	List<CommodityDto> selectAllCommodityBySortId(@Param("sortId") Long sortId, @Param("parentId") Long parentId);
}
