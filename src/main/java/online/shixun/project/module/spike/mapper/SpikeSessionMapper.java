package online.shixun.project.module.spike.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import online.shixun.project.module.spike.dto.SpikeSessionDto;

/**
 * 
 * @ClassName: SpikeSessionMapper.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: am
 * @date: 2019年10月26日 下午3:51:33
 */
@Mapper
public interface SpikeSessionMapper {

	/**
	 * 查询全部有效场次
	 * @return 有效的秒杀活动场次集合
	 */
	List<SpikeSessionDto> selectAllSpikeSession();
}
