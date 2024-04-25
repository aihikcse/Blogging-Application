package org.blogg.Service.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.blogg.Exception.RecourseNotFoundException;
import org.blogg.Model.Category;
import org.blogg.Model.Post;
import org.blogg.Model.User;
import org.blogg.Payloads.PostDto;
import org.blogg.Payloads.PostResponse;
import org.blogg.Repository.CategoryRepository;
import org.blogg.Repository.PostRepository;
import org.blogg.Repository.UserRepository;
import org.blogg.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = userRepository.findById(userId).orElseThrow(()->
				new RecourseNotFoundException("user", "userId", userId));
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->
				new RecourseNotFoundException("category", "categoryId", categoryId));
		Post posts = modelMapper.map(postDto, Post.class);
		posts.setImageName("default.png");
		posts.setAddedDate(new Date());
		posts.setUser(user);
		posts.setCategory(category);
		Post newPost = postRepository.save(posts);
		return PostToPostDto(modelMapper.map(newPost, Post.class));
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post existingPost = postRepository.findById(postId).orElseThrow(()->
				new RecourseNotFoundException("post", "postId", postId));
		existingPost.setTitle(postDto.getTitle());
		existingPost.setContent(postDto.getContent());
//		existingPost.setImageName(postDto.getImageName());
//		existingPost.setAddedDate(postDto.getAddedDate());
		postRepository.save(existingPost);
		return PostToPostDto(existingPost);
	}

	@Override
	public void deletePost(Integer postId) {
		Post existingPost = postRepository.findById(postId).orElseThrow(()-> new RecourseNotFoundException("post", "postId", postId));
		postRepository.delete(existingPost);
	}

	@Override
	public PostDto findById(Integer postId) {
		Post existingPost = postRepository.findById(postId).orElseThrow(()->
				new RecourseNotFoundException("post", "postId", postId));
		return PostToPostDto(existingPost);
	}

	@Override
	public List<PostDto> findPostByUser(Integer userId) {
		User existingUser = userRepository.findById(userId).orElseThrow(()->
				new RecourseNotFoundException("User", "userId", userId));
		List<Post> posts = postRepository.findByUser(existingUser);
		List<PostDto> postDtos = posts.stream().map(post ->
				(modelMapper.map(post, PostDto.class))).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> findPostByCategory(Integer categoryId) {
		Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(()->
				new RecourseNotFoundException("Category", "categoryId", categoryId));
		List<Post> posts = postRepository.findByCategory(existingCategory);
		List<PostDto> postDtos = posts.stream().map(post->
				(modelMapper.map(post, PostDto.class))).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public PostResponse viewAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc"))
			sort = Sort.by(sortBy).ascending();
		else
			sort = Sort.by(sortBy).descending();
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost = postRepository.findAll(p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map(post->PostToPostDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	// Model Mapper methods -->
	public Post PostDtoToPost(PostDto postDto){
		return modelMapper.map(postDto, Post.class);
	}
	public PostDto PostToPostDto(Post post){
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepository.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
