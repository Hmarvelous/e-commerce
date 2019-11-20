package online.shixun.project.module.spike.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import online.shixun.project.module.spike.dto.SpikeCommodityDto;

/**
 * 
 * @ClassName: SpikeCommodityMapper.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: am
 * @date: 2019年10月29日 下午1:53:19
 */
@Mapper
public interface SpikeCommodityMapper {

	/**
	 * 通过商品ID查询商品信息
	 * @param id 商品ID
	 * @return 查询的商品对象
	 */
	SpikeCommodityDto selectSpikeCommodityById(@Param("id") long id);
	
	/**
	 * 查询全部有效秒杀商品信息
	 * @return 秒杀商品集合
	 */
	List<SpikeCommodityDto> selectAllCommoditys();
	
	/**
	 * 更新秒杀商品库存
	 * @param id 秒杀商品ID
	 * @param count 购买量
	 * @return 返回1为更新成功  返回0为更新失败
	 */
	int updateSpikeCommodityStock(@Param("id") long id, @Param("count") int count);
	
	/**
	 * 商品库存自增1
	 * @param id 秒杀商品库存
	 * @return 返回1为增加成功  返回0为增加失败
	 */
	int updateSpikeCommodityStockincrement(@Param("id") long id);
}
