package org.blogg.Controller;

import org.blogg.Config.AppConstants;
import org.blogg.Payloads.PostDto;
import org.blogg.Payloads.PostResponse;
import org.blogg.Service.FileService;
import org.blogg.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/post")
public class PostController {
	@Autowired
	public PostService postService;
	
	@Autowired
	public FileService fileService;
	
	@Value("${project.image}")
	public String path;
	
	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}



	@PostMapping("/user/{userId}/category/{categoryId}/createPost")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
											  @PathVariable Integer userId,
											  @PathVariable Integer categoryId){
		return new ResponseEntity<PostDto>(postService.createPost(postDto, userId, categoryId), HttpStatus.CREATED);
		
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(
			@RequestBody PostDto postDto, 
			@PathVariable("postId") Integer postId){
		return new ResponseEntity<PostDto>(postService.updatePost(postDto, postId), HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<String> deletePost(@PathVariable("postId") Integer postId){
		postService.deletePost(postId);
		return new ResponseEntity<String>("The post with id: "+postId+" is deleted successfully.", HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> findPost(@PathVariable("postId") Integer postId){
		return new ResponseEntity<>(postService.findById(postId), HttpStatus.OK);
	}
	
	@GetMapping("/viewAllPost")
	public ResponseEntity<PostResponse> findAllPost( 
			@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER, required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE, required=false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY, required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR, required=false) String sortDir
			){
		 PostResponse postResponse = postService.viewAllPost(pageNumber, pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> findPostByUser(@PathVariable Integer userId){
		return new ResponseEntity<>(postService.findPostByUser(userId), HttpStatus.OK);
	}

	@GetMapping("/category/{categoryId}/categories")
	public ResponseEntity<List<PostDto>> findPostByCategory(@PathVariable Integer categoryId){
		return new ResponseEntity<List<PostDto>>(postService.findPostByCategory(categoryId), HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
		List<PostDto> posts = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image ,
			@PathVariable Integer postId
			) throws IOException{
	PostDto postDto = this.postService.findById(postId);
	String fileName = this.fileService.uploadImage(path, image);
	postDto.setImageName(fileName);
	PostDto updatePost = this.postService.updatePost(postDto, postId);
	return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	//method to serve files
	@GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {
	InputStream resource = this.fileService.getResource(path, imageName);
	response.setContentType (MediaType.IMAGE_JPEG_VALUE); 
	StreamUtils.copy(resource, response.getOutputStream());
	}
}
