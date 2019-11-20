package online.shixun.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security配置类
 * @author am
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	/**
	 * 自定义用户处理处理服务
	 */
	@Bean
	public MyUserDetailsService userDetailsService() {
		return new MyUserDetailsService();
	}
	
	/**
	 * 自定义加密类
	 */
	@Bean
	public MyPasswordEncoder passwordEncoder() {
		return new MyPasswordEncoder();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 设置虚拟账户
//		auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
		
		
		// 替换自定义类
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			// 不需要认证就可以访问
			.antMatchers("/",
					     "/index",
					     "/static/**",
					     "/member/login",
					     "/member/register",
					     "/member/doRegister",
					     "/commodity/details",
					     "/comment/getComments",
					     "/commodity/search",
					     "/commodity/sort",
					     "/spike/spikeDetails",
					     "/spike/spikeDetails/*",
					     "/spike/spikeSession"
					     ).permitAll()
			// 其它未匹配的都需要认证才能访问
			.anyRequest().authenticated()
			.and()
			// 登录相关
			.formLogin()
			.loginPage("/member/login").loginProcessingUrl("/login").defaultSuccessUrl("/", true).failureUrl("/member/login?code=201")
			.and()
			// 注销相关
			.logout()
			.logoutUrl("/logout").logoutSuccessUrl("/index")
			.and()
			// 禁用csrf
			.csrf().disable();
	}

}
