package online.shixun.project.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 * @ClassName: RabbitConfig.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: am
 * @date: 2019年10月30日 下午7:26:03
 */
@Configuration
public class RabbitConfig {

	/**
	 * 秒杀队列
	 */
	public static final String SECKILL_QUEUE = "seckill";
	
	
	@Bean
	public Queue seckillQueue() {
		return new Queue(SECKILL_QUEUE, true);
	}
}
