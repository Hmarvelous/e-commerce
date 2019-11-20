package online.shixun.project.module.advertise.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import online.shixun.project.module.advertise.dto.CarouselAdDto;

/**
 * 
 * @author am
 *
 */
@Mapper
public interface CarouselAdMapper {

	/**
	 * 获取六条有效轮播广告
	 * @return
	 */
	List<CarouselAdDto> selectSixEffectiveCarouselAds();
}
