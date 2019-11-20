package online.shixun.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement // 开启事务支持
@MapperScan({"online.shixun.project.module.member.mapper",
			 "online.shixun.project.module.advertise.mapper",
			 "online.shixun.project.module.commodity.mapper",
			 "online.shixun.project.module.order.mapper",
			 "online.shixun.project.module.spike.mapper"})
public class ECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}
}
