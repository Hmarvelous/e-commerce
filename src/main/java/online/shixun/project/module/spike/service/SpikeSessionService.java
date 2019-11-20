package online.shixun.project.module.spike.service;

import java.util.List;

import online.shixun.project.module.spike.dto.SpikeSessionDto;

/**
 * 秒杀场次服务接口类
 * @ClassName: SpikeSessionService.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: am
 * @date: 2019年10月26日 下午4:04:29
 */
public interface SpikeSessionService {

	/**
	 * 查询全部有效场次
	 * @return 有效的秒杀活动场次集合
	 */
	List<SpikeSessionDto> getAllSpikeSession();
}
