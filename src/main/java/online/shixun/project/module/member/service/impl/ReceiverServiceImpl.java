package online.shixun.project.module.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.shixun.project.module.member.dto.ReceiverDto;
import online.shixun.project.module.member.mapper.ReceiverMapper;
import online.shixun.project.module.member.service.ReceiverService;

/**
 * 收货地址服务接口实现类
 * @author am
 *
 */
@Service
public class ReceiverServiceImpl implements ReceiverService {

	@Autowired
	private ReceiverMapper receiverMapper;
	
	/**
	 * 通过用户名查询该用户的四条收货地址
	 */
	@Override
	public List<ReceiverDto> getFourReceiverByUsername(String username) {
		return receiverMapper.selectFourReceiverByUsername(username);
	}

	/**
	 * 通过用户名查询该用户的全部收货地址
	 */
	@Override
	public List<ReceiverDto> getAllReceiverByUsername(String username) {
		return receiverMapper.selectAllReceiverByUsername(username);
	}

	/**
	 * 根据收货地址ID查询收货地址信息
	 */
	@Override
	public ReceiverDto getReceiverByID(Long id, String username) {
		return receiverMapper.selectReceiverByID(id, username);
	}

	
}
