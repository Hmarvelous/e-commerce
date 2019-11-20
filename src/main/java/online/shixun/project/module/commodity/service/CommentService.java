package online.shixun.project.module.commodity.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import online.shixun.project.module.commodity.dto.CommentDto;
import online.shixun.project.module.member.dto.MemberDto;

/**
 * 商品评论服务接口类
 * @author am
 *
 */
public interface CommentService {

	/**
	 * 根据评论等级以及商品ID全部评论信息
	 * @param level
	 * @param commodityId
	 * @return
	 */
	List<CommentDto> getAllCommentsByLevel(Integer level, Long commodityId);
	
	/**
	 * 获取商品评论分页信息
	 * @param level
	 * @param commodityId
	 * @param pageNow
	 * @param pageSize
	 * @return
	 */
	PageInfo<CommentDto> getCommentsPage(Integer level, Long commodityId, Integer pageNow, Integer pageSize);
	
	/**
	 * 根据评论等级以及商品ID评论数量
	 * @param level
	 * @param commodityId
	 * @return
	 */
	Integer getAllCommentsCountByLevel(Integer level, Long commodityId);
	
	/**
	 * 根据商品ID插入商品评论信息
	 * @param orderNumber 订单号
	 * @param commodityId 商品ID
	 * @param comment 评论对象
	 * @param member 会员对象
	 * @return 是否添加成功
	 */
	boolean addCommentByCommentId(Long orderNumber, Long commodityId, CommentDto comment, MemberDto member);
}
