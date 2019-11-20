package online.shixun.project.module.member.service;

import online.shixun.project.module.member.dto.MemberDto;

/**
 * 会员服务接口类
 * @author am
 *
 */
public interface MemberService {

	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	MemberDto getMemberByUsername(String username);
	
	/**
	 * 更新会员信息
	 * @param member 会员对象
	 * @return 是否修改成功
	 */
	boolean updateMember(MemberDto member);
	
	/**
	 * 注册会员
	 * @param member 会员对象
	 * @return 是否注册成功
	 */
	boolean registerMember(MemberDto member);
}
