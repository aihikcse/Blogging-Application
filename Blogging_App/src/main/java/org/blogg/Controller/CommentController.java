package org.blogg.Controller;

import org.blogg.Payloads.CommentDto;
import org.blogg.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/createComment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
													@PathVariable Integer postId)
	{
		CommentDto createComment = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Integer commentId)
	{
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<String>("The comment with id: "+commentId+" is deleted successfully.", HttpStatus.OK);
	}
}
