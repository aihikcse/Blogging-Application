package org.blogg.Service;

import java.util.List;


import org.blogg.Payloads.PostDto;
import org.blogg.Payloads.PostResponse;

public interface PostService {

	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	public PostDto updatePost(PostDto postDto, Integer postId);
	public void deletePost(Integer postId);
	public PostDto findById(Integer postId);
	public List<PostDto> findPostByUser(Integer userId);
	public List<PostDto> findPostByCategory(Integer categoryId);
	public PostResponse viewAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	public List<PostDto> searchPosts(String keyword);
}