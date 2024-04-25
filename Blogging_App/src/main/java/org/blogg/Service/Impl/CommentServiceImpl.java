package org.blogg.Service.Impl;

import java.util.List;

import org.blogg.Exception.RecourseNotFoundException;
import org.blogg.Model.Comment;
import org.blogg.Model.Post;
import org.blogg.Payloads.CommentDto;
import org.blogg.Repository.PostRepository;
import org.blogg.Repository.CommentRepo;
import org.blogg.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	private PostRepository postRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CommentRepo commentRepo;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = postRepo.findById(postId).orElseThrow(()->
				new RecourseNotFoundException("Post","postId",postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment newComment = this.commentRepo.save(comment);
		
		return modelMapper.map(newComment,CommentDto.class);
	}

	@Override
	public Comment updateComment(CommentDto commentDto, Integer commentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->
			new RecourseNotFoundException("Comment","commentId",commentId));
		this.commentRepo.delete(comment);
	}

	@Override
	public List<Comment> getAllComment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment getCommentById(Integer commentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
