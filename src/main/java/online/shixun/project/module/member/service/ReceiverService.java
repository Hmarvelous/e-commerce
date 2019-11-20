package online.shixun.project.module.member.service;

import java.util.List;

import online.shixun.project.module.member.dto.ReceiverDto;

/**
 * 收货地址服务接口类
 * @author am
 *
 */
public interface ReceiverService {

	/**
	 * 通过用户名查询该用户的四条收货地址
	 * @param username 用户名
	 * @return 收货地址集合
	 */
	List<ReceiverDto> getFourReceiverByUsername(String username);
	
	/**
	 * 通过用户名查询该用户的全部收货地址
	 * @param username 用户名
	 * @return 收货地址集合
	 */
	List<ReceiverDto> getAllReceiverByUsername(String username);
	
	/**
	 * 根据收货地址ID查询收货地址信息
	 * @param id 收货地址ID
	 * @param username 用户名
	 * @return 收货地址对象
	 */
	ReceiverDto getReceiverByID(Long id, String username);
}
