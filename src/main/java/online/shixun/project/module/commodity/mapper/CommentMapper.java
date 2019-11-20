package online.shixun.project.module.commodity.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import online.shixun.project.module.commodity.dto.CommentDto;

/**
 * 
 * @author am
 *
 */
@Mapper
public interface CommentMapper {

	/**
	 * 根据评论等级以及商品ID全部评论信息
	 * @param level 商品级别
	 * @param commodityId 商品ID
	 * @return 评论对象集合
	 */
	List<CommentDto> selectAllCommentsByLevel(@Param("level") Integer level, @Param("commodityId") Long commodityId);
	
	/**
	 * 根据评论等级以及商品ID评论数量
	 * @param level 评论级别
	 * @param commodityId 商品ID
	 * @return 返回1为成功  返回0为失败
	 */
	Integer selectAllCommentsCountByLevel(@Param("level") Integer level, @Param("commodityId") Long commodityId);
	
	/**
	 * 根据商品ID插入商品评论信息
	 * @param commodityId 商品ID
	 * @param comment 评论对象
	 * @return 返回1为成功  返回0为失败
	 */
	Integer insertCommentByCommentId(@Param("commodityId") Long commodityId, @Param("comment") CommentDto comment);
}
