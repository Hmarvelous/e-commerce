package online.shixun.project.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import online.shixun.project.module.advertise.dto.CarouselAdDto;
import online.shixun.project.module.advertise.service.CarouselAdService;

/**
 * 
 * @author am
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CarouselAdServiceTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CarouselAdService carouselAdService;
	
	/**
	 * 获取全部有效轮播广告
	 */
	@Test
	public void getAlleffectiveCarouselAds() {
		List<CarouselAdDto> carouselAds = carouselAdService.getSixEffectiveCarouselAds();
		logger.info(carouselAds.toString());
	}
}
