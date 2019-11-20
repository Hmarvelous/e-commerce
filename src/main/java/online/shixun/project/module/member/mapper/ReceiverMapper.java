package online.shixun.project.module.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import online.shixun.project.module.member.dto.ReceiverDto;

/**
 * 
 * @author am
 *
 */
@Mapper
public interface ReceiverMapper {

	/**
	 * 通过用户名查询该用户的四条收货地址
	 * @param username 用户名
	 * @return 收货地址集合
	 */
	List<ReceiverDto> selectFourReceiverByUsername(@Param("username") String username);
	
	/**
	 * 通过用户名查询该用户的全部收货地址
	 * @param username 用户名
	 * @return 收货地址集合
	 */
	List<ReceiverDto> selectAllReceiverByUsername(@Param("username") String username);
	
	/**
	 * 根据收货地址ID查询收货地址信息
	 * @param id 收货地址ID
	 * @param username 用户名
	 * @return 收货地址对象
	 */
	ReceiverDto selectReceiverByID(@Param("id") Long id, @Param("username") String username);
}
