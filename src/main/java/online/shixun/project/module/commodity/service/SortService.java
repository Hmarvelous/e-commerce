package online.shixun.project.module.commodity.service;

import java.util.List;

import online.shixun.project.module.commodity.dto.SortDto;

/**
 * 商品分类服务接口类
 * @author am
 *
 */
public interface SortService {

	/**
	 * 根据父级分类ID获取商品分类信息
	 * @return
	 */
	List<SortDto> getSortsByParentId(Long parentId);
	
	/**
	 * 获取全部商品分类信息
	 * @return
	 */
	List<SortDto> getAllSorts();
	
	/**
	 * 根据分类父级ID查询第一个分类ID
	 * @param parentId 父级分类ID
	 * @return 子类ID第一个分类ID
	 */
	Long getSortIdByParentId(Long parentId);
}
