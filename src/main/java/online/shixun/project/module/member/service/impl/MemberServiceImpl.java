package online.shixun.project.module.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.shixun.project.core.Constant;
import online.shixun.project.module.member.dto.MemberDto;
import online.shixun.project.module.member.mapper.MemberMapper;
import online.shixun.project.module.member.service.MemberService;
import online.shixun.project.util.MD5Utils;

/**
 * 会员接口实现类
 * @author am
 *
 */
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	/**
	 * 根据用户名查询用户信息
	 */
	@Override
	public MemberDto getMemberByUsername(String username) {
		return memberMapper.selectMemberByUsername(username);
	}

	/**
	 * 更新会员信息
	 */
	@Override
	public boolean updateMember(MemberDto member) {
		return memberMapper.updateMember(member) == 1;
	}

	/**
	 * 注册会员
	 */
	@Override
	public boolean registerMember(MemberDto member) {
		// 密码加密
		member.setPassword(MD5Utils.stringToMD5(member.getPassword() + Constant.PASSWORD_SALT));
		return memberMapper.insertMember(member) == 1;
	}

}
