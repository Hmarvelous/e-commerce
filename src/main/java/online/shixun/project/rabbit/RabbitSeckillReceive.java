package online.shixun.project.rabbit;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import online.shixun.project.module.spike.service.SpikeCommodityService;

/**
 * RabbitMQ秒杀接收类
 * @ClassName: RabbitSeckillReceive.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: am
 * @date: 2019年10月30日 下午7:26:19
 */
@Component
@RabbitListener(queues = RabbitConfig.SECKILL_QUEUE)
public class RabbitSeckillReceive {
	
	@Autowired
	private SpikeCommodityService spikeCommodityService;
	
	/**
	 * 秒杀请求接收
	 */
	@RabbitHandler
	public void seckillReceive(Map<String, Object> map) {
		
		// 处理秒杀业务
		spikeCommodityService.seckill((long)map.get("commodityId"), (String)map.get("parameters"), (String)map.get("username"));
	}
}
