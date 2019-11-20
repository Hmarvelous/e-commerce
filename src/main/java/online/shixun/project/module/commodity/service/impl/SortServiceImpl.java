package online.shixun.project.module.commodity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import online.shixun.project.module.commodity.dto.SortDto;
import online.shixun.project.module.commodity.mapper.SortMapper;
import online.shixun.project.module.commodity.service.SortService;

/**
 * 商品分类服务接口实现类
 * @author am
 *
 */
@Service
public class SortServiceImpl implements SortService {

	@Autowired
	private SortMapper sortMapper;
	
	/**
	 * 根据父级分类ID获取商品分类信息
	 */
	@Override
	public List<SortDto> getSortsByParentId(Long parentId) {
		return sortMapper.selectSortsByParentId(parentId);
	}

	/**
	 * 获取全部商品分类信息
	 */
	@Override
	@Cacheable(cacheNames = "allSort")
	public List<SortDto> getAllSorts() {
		// 获取一级分类
		List<SortDto> sorts = getSortsByParentId(null);
		// 遍一级分类获取二级分类
		for (SortDto sort : sorts) {
			List<SortDto> secondarySort = getSortsByParentId(sort.getId());
			// 遍历二级分类获取三级分类
			for (SortDto secondary : secondarySort) {
				List<SortDto> thirdSort = getSortsByParentId(secondary.getId());
				secondary.setParentSorts(thirdSort);
			}
			sort.setParentSorts(secondarySort);
		}
		return sorts;
	}

	/**
	 * 根据分类父级ID查询第一个分类ID
	 */
	@Override
	public Long getSortIdByParentId(Long parentId) {
		return sortMapper.selectSortIdByParentId(parentId);
	}


}
