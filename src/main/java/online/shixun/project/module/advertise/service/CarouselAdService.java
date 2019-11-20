package online.shixun.project.module.advertise.service;

import java.util.List;

import online.shixun.project.module.advertise.dto.CarouselAdDto;

/**
 * 轮播广告服务接口类
 * @author am
 *
 */
public interface CarouselAdService {

	/**
	 * 获取六条有效轮播广告
	 * @return
	 */
	List<CarouselAdDto> getSixEffectiveCarouselAds();
}
