package online.shixun.project.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.PageInfo;

import online.shixun.project.module.commodity.dto.CommentDto;
import online.shixun.project.module.commodity.service.CommentService;

/**
 * 
 * @author am
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CommentService commentService;
	
	
	/**
	 * 测试根据评论等级以及商品ID全部评论信息
	 */
	@Test
	public void getAllCommentByLevel() {
		List<CommentDto> comments = commentService.getAllCommentsByLevel(0, 2L);
		for (CommentDto comment : comments) {
			logger.info(comment.toString());
		}
	}
	
	/**
	 * 测试获取商品评论分页信息
	 */
	@Test
	public void getCommentsPage() {
		PageInfo<CommentDto> pageInfo = commentService.getCommentsPage(0, 2L, 1, 2);
		logger.info(pageInfo.toString());
	}
}
