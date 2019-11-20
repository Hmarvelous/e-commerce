package online.shixun.project.module.commodity.dto;


import java.text.SimpleDateFormat;
import java.util.Date;

import online.shixun.project.module.member.dto.MemberDto;

/**
 * 商品评论实体类
 * @author am
 *
 */
public class CommentDto {

	// 评论ID
	private Long id;
	
	// 评论内容
	private String content;
	
	// 评论人
	private MemberDto member;
	
	// 评论时间
	private Date commentDate;
	
	// 评论等级(0:好评，1:中评，2:差评)
	private Integer commentLevel;

	
	
	public CommentDto() { }

	public CommentDto(String content, MemberDto member, Date commentDate, Integer commentLevel) {
		this.content = content;
		this.member = member;
		this.commentDate = commentDate;
		this.commentLevel = commentLevel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public MemberDto getMember() {
		return member;
	}

	public void setMember(MemberDto member) {
		this.member = member;
	}

	public Date getCommentDate() {
		return commentDate;
	}
	
	public String getCommentDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(commentDate);
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	public Integer getCommentLevel() {
		return commentLevel;
	}

	public void setCommentLevel(Integer commentLevel) {
		this.commentLevel = commentLevel;
	}

	@Override
	public String toString() {
		return "CommentDto [id=" + id + ", content=" + content + ", member=" + member + ", commentDate=" + commentDate.toString()
				+ ", commentLevel=" + commentLevel + "]";
	}

}
