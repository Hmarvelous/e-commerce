package online.shixun.project.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import online.shixun.project.module.member.dto.MemberDto;
import online.shixun.project.module.member.service.MemberService;

/**
 * 自定义用户处理处理服务
 * @author am
 *
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MemberService memberService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
//		logger.info("当前登录的用户：" + username);
		
		UserDetails user = null;
		MemberDto member = null;
		
		// 判断username是否为空
		if (username == null) {
			throw new UsernameNotFoundException("用户名不能为空");
		}
		
		// 根据用户名查询用户信息
		member = memberService.getMemberByUsername(username);
		if (member == null) {
			throw new UsernameNotFoundException("该用户不存在");
		}
		
		logger.info(member.toString());
		
		user = new User(member.getUsername(), member.getPassword(), true, true, true, member.getFreeze(), getAuthorities());
		
		return user;
	}

	
	/**
	 * 获取会员权限
	 * @return
	 */
	public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(1);

        authList.add(new SimpleGrantedAuthority("USER"));

        return authList;
    }
}
