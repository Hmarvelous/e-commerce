package online.shixun.project.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import online.shixun.project.module.member.dto.MemberDto;
import online.shixun.project.module.member.service.MemberService;

/**
 * 
 * @author am
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MemberService memberService;

	@Value("${spring.datasource.url}")
	private String smsIp;
	
	/**
	 * 测试根据用户名查询用户信息
	 */
	@Test
	public void getMemberByUsername() {
		MemberDto member = memberService.getMemberByUsername("test1");
		logger.info(member.toString());
	}
	
	
	@Test
	public void test1() {
		logger.info(smsIp);
	}
}
