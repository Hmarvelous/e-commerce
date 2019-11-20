package online.shixun.project.module.commodity.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import online.shixun.project.module.commodity.dto.SortDto;

/**
 * 
 * @author am
 *
 */
@Mapper
public interface SortMapper {

	/**
	 * 根据父级分类ID获取商品分类信息
	 * @return
	 */
	List<SortDto> selectSortsByParentId(@Param("parentId") Long parentId);
	
	/**
	 * 根据分类父级ID查询第一个分类ID
	 * @param parentId 父级分类ID
	 * @return 子类ID第一个分类ID
	 */
	Long selectSortIdByParentId(@Param("parentId") Long parentId);
}
