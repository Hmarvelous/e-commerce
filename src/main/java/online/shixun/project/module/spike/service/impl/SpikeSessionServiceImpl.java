package online.shixun.project.module.spike.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import online.shixun.project.module.spike.dto.SpikeCommodityDto;
import online.shixun.project.module.spike.dto.SpikeSessionDto;
import online.shixun.project.module.spike.mapper.SpikeSessionMapper;
import online.shixun.project.module.spike.service.SpikeSessionService;

/**
 * 商秒杀场次服务接口实现类
 * @ClassName: SpikeSessionServiceImpl.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: am
 * @date: 2019年10月26日 下午4:05:57
 */
@Service
public class SpikeSessionServiceImpl implements SpikeSessionService {

	@Autowired
	private SpikeSessionMapper spikeSessionMapper;
	
	/**
	 * 查询全部有效场次
	 */
	@Override
	@Cacheable(cacheNames = "allSpikeSession")
	public List<SpikeSessionDto> getAllSpikeSession() {
		List<SpikeSessionDto> spikeSessions = spikeSessionMapper.selectAllSpikeSession();
		// 判断商品活动状态
		for (SpikeSessionDto spikeSession : spikeSessions) {
			for (SpikeCommodityDto spikeCommodity : spikeSession.getSpikeCommoditys()) {
				// 计算时间差
				long timeDifference = (spikeCommodity.getStartTime().getTime() - System.currentTimeMillis());
				if (timeDifference < 0) {
					if (spikeCommodity.getStore() <= 0) {
						// 已结束,库存已为0
						spikeCommodity.setActiveStatus(0);
					} else if (spikeCommodity.getStore() > 0 && -timeDifference < 30*60*1000) {
						// 正在开始秒杀,库存未完
						spikeCommodity.setActiveStatus(1);
					} else {
						// 已结束,时间结束,超过30分钟
						spikeCommodity.setActiveStatus(2);
					}
				} else if (timeDifference >= 0 && timeDifference < 24*60*60*1000) {
					// 进行倒计时
					spikeCommodity.setActiveStatus(3);
				} else {
					// 超出一天的时间范围,等待开始
					spikeCommodity.setActiveStatus(4);
				}
			}
		}
		return spikeSessions;
	}

	
}
