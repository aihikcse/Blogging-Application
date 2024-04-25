package org.blogg.Service;

import java.util.List;

import org.blogg.Model.Comment;
import org.blogg.Payloads.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentDto,Integer postId);
	Comment updateComment(CommentDto commentDto,Integer commentId);
	void deleteComment(Integer commentId);
	List<Comment> getAllComment();
	Comment getCommentById(Integer commentId);
}
