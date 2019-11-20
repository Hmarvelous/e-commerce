package online.shixun.project.module.commodity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import online.shixun.project.module.commodity.dto.CommentDto;
import online.shixun.project.module.commodity.mapper.CommentMapper;
import online.shixun.project.module.commodity.service.CommentService;
import online.shixun.project.module.member.dto.MemberDto;
import online.shixun.project.module.order.dto.OrderItemDto;
import online.shixun.project.module.order.enumerate.OrderStatusEnum;
import online.shixun.project.module.order.service.OrderItemService;
import online.shixun.project.module.order.service.OrderService;

/**
 * 商品评论服务实现类
 * @author am
 *
 */
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private OrderService orderService;
	
	/**
	 * 根据评论等级以及商品ID全部评论信息
	 */
	@Override
	public List<CommentDto> getAllCommentsByLevel(Integer level, Long commodityId) {
		return commentMapper.selectAllCommentsByLevel(level, commodityId);
	}

	/**
	 * 获取商品评论分页信息
	 */
	@Override
	public PageInfo<CommentDto> getCommentsPage(Integer level, Long commodityId, Integer pageNow, Integer pageSize) {
		if (commodityId == null) {
			return null;
		}
		if (pageNow == null) {
			pageNow = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		// 设置获取数据的页码以及获取数量
		PageHelper.startPage(pageNow, pageSize);
		List<CommentDto> comments = getAllCommentsByLevel(level, commodityId);
		PageInfo<CommentDto> pageInfo = new PageInfo<CommentDto>(comments);
		return pageInfo;
	}

	/**
	 * 根据评论等级以及商品ID评论数量
	 */
	@Override
	public Integer getAllCommentsCountByLevel(Integer level, Long commodityId) {
		return commentMapper.selectAllCommentsCountByLevel(level, commodityId);
	}

	/**
	 * 根据商品ID插入商品评论信息
	 */
	@Override
	@Transactional
	public boolean addCommentByCommentId(Long orderNumber, Long commodityId, CommentDto comment, MemberDto member) {
		// 添加评论
		commentMapper.insertCommentByCommentId(commodityId, comment);
		// 更新订单项状态
		orderItemService.updateOrderItemStatus(commodityId, orderService.getOrderByOrderNumber(orderNumber, member.getUsername()).getId(), OrderStatusEnum.COMPLETE);
		// 获取订单项集合
		List<OrderItemDto> orderItems = orderItemService.getOrderItemByOrderNumber(orderNumber, member.getUsername());
		// 判断订单项状态是否都已经评价,如果全部评价则更新订单的状态
		for (OrderItemDto orderItem : orderItems) {
			// 如果全部订单项都交易完毕了,则更新订单为交易完毕
			if (orderItem.getOrderItemStatus().getOrdinal() == OrderStatusEnum.COMPLETE.getOrdinal()) {
				orderService.updateOrderStatus(orderNumber, OrderStatusEnum.COMPLETE, member.getId());
			}
		}
		return true;
	}

}
