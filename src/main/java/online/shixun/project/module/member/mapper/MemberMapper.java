package online.shixun.project.module.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import online.shixun.project.module.member.dto.MemberDto;

/**
 * 
 * @author am
 *
 */
@Mapper
public interface MemberMapper {

	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	MemberDto selectMemberByUsername(@Param("username") String username);
	
	/**
	 * 更新会员信息
	 * @param member 会员对象
	 * @return 返回1为更新成功  返回0为更新失败
	 */
	Integer updateMember(@Param("member") MemberDto member);
	
	
	/**
	 * 添加会员
	 * @param member 会员对象
	 * @return 返回1为添加成功  返回0为添加失败
	 */
	int insertMember(@Param("member") MemberDto member);
}
