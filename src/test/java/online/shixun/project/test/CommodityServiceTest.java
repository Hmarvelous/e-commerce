package online.shixun.project.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import online.shixun.project.module.commodity.dto.CommodityDto;
import online.shixun.project.module.commodity.service.CommodityService;

/**
 * 
 * @author am
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommodityServiceTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CommodityService commodityService;
	
	
	/**
	 * 测试根据商品ID获取商品详细信息
	 */
	@Test
	public void getDetailsCommodityById() {
		CommodityDto commodity = commodityService.getDetailsCommodityById(2L);
		logger.info(commodity.toString());
	}
}
