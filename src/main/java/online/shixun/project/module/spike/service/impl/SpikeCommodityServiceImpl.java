package online.shixun.project.module.spike.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import online.shixun.project.module.spike.dto.SpikeCommodityDto;
import online.shixun.project.module.spike.mapper.SpikeCommodityMapper;
import online.shixun.project.module.spike.service.SpikeCommodityService;

/**
 * 秒杀商品服务接口实现类
 * @ClassName: SpikeCommodityServiceImpl.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: am
 * @date: 2019年10月29日 下午2:03:41
 */
@Service
public class SpikeCommodityServiceImpl implements SpikeCommodityService {

	@Autowired
	private SpikeCommodityMapper spikeCommodityMapper;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 通过商品ID查询商品信息
	 */
	@Override
	public SpikeCommodityDto getSpikeCommodityById(long id) {
		return spikeCommodityMapper.selectSpikeCommodityById(id);
	}

	/**
	 * 查询全部有效秒杀商品信息
	 */
	@Override
	public List<SpikeCommodityDto> getAllCommoditys() {
		return spikeCommodityMapper.selectAllCommoditys();
	}
	
	
	/**
	 * 使用乐观锁进行秒杀
	 */
	@Override
	@Transactional
	public void seckill(long id, String parameters, String username) {
		JSONObject json = new JSONObject();
		
		// 查询秒杀商品信息
		SpikeCommodityDto spikeCommodity = getSpikeCommodityById(id);
		if (spikeCommodity.getStore() <= 0) {
			// 商品已售完,写入状态订单
			redisTemplate.opsForValue().set("state_order_" + id + "_" + username, "商品已售完", 60*60, TimeUnit.SECONDS);
			return;
		}
		
		// 减库存
		if (spikeCommodityMapper.updateSpikeCommodityStock(id, 1) <= 0) {
			// 该用户购买失败
			redisTemplate.opsForValue().increment("spike_count_" + id);
			// 生成秒杀订单
			redisTemplate.opsForValue().set("state_order_" + id + "_" + username, "购买失败", 60*60, TimeUnit.SECONDS);
			return;
		}
		
		
		// 商品ID
		json.put("commodityId", spikeCommodity.getCommodity().getId());
		// 商品选定参数
		json.put("parameters", parameters);
		// 商品数量
		json.put("count", 1);
		// 秒杀商品ID
		json.put("spikeCommodityId", id);
		// 写入redis订单
		json.put("result", "待付款");
		redisTemplate.opsForValue().set("state_order_" + id + "_" + username, json, 60*60, TimeUnit.SECONDS);
	}

	/**
	 * 商品库存自增1
	 */
	@Override
	public boolean updateSpikeCommodityStockincrement(long id) {
		return spikeCommodityMapper.updateSpikeCommodityStockincrement(id) == 1;
	}

}
