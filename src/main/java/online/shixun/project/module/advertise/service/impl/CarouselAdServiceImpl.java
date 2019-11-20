package online.shixun.project.module.advertise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.shixun.project.module.advertise.dto.CarouselAdDto;
import online.shixun.project.module.advertise.mapper.CarouselAdMapper;
import online.shixun.project.module.advertise.service.CarouselAdService;

/**
 * 轮播广告接口实现类
 * @author am
 *
 */
@Service
public class CarouselAdServiceImpl implements CarouselAdService {

	@Autowired
	private CarouselAdMapper carouselAdMapper;
	
	/**
	 * 获取六条有效轮播广告
	 */
	@Override
	public List<CarouselAdDto> getSixEffectiveCarouselAds() {
		return carouselAdMapper.selectSixEffectiveCarouselAds();
	}

}
