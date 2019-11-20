package online.shixun.project.module.spike.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import online.shixun.project.module.spike.dto.SpikeCommodityDto;

/**
 * 秒杀商品服务接口类
 * @ClassName: SpikeCommodityService.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: am
 * @date: 2019年10月29日 下午2:03:14
 */
public interface SpikeCommodityService {

	/**
	 * 通过商品ID查询商品信息
	 * @param id 商品ID
	 * @return 查询的商品对象
	 */
	SpikeCommodityDto getSpikeCommodityById(long id);
	
	/**
	 * 查询全部有效秒杀商品信息
	 * @return 秒杀商品集合
	 */
	List<SpikeCommodityDto> getAllCommoditys();
	
	/**
	 * 使用乐观锁进行秒杀
	 * @param id 秒杀商品ID
	 * @param parameters 秒杀商品参数
	 * @param username 秒杀用户的用户名
	 */
	void seckill(long id, String parameters, String username);
	
	/**
	 * 商品库存自增1
	 * @param id 秒杀商品库存
	 * @return 增加是否成功
	 */
	boolean updateSpikeCommodityStockincrement(@Param("id") long id);
}
