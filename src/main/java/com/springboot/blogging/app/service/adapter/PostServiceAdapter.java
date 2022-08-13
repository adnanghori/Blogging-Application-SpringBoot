package com.springboot.blogging.app.service.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.blogging.app.exception.ResourceNotFoundException;
import com.springboot.blogging.app.model.Category;
import com.springboot.blogging.app.model.Post;
import com.springboot.blogging.app.model.User;
import com.springboot.blogging.app.payload.PostDTO;
import com.springboot.blogging.app.repository.CategoryRepository;
import com.springboot.blogging.app.repository.PostRepository;
import com.springboot.blogging.app.repository.UserRepository;
import com.springboot.blogging.app.service.PostService;
import com.springboot.blogging.app.utility.FileUpload;
import com.springboot.blogging.app.utility.PostResponse;
@Service
public class PostServiceAdapter implements PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private FileUpload fileUpload;
	@Override
	public PostDTO createPost(PostDTO postDTO) {
		 Post post = this.modelMapper.map(postDTO, Post.class);
		 this.postRepository.save(post);
		return this.modelMapper.map(post, PostDTO.class);
	}
// sorting
	@Override
	public PostResponse getAllPosts(Integer pageNumber , Integer pageSize,String sortBy,String sortDir) {
	
		Pageable  pageable = PageRequest.of(pageNumber, pageSize,Sort.by(Direction.valueOf(sortDir.toUpperCase()),sortBy));
		
		Page<Post> pagePost = this.postRepository.findAll(pageable);
		List<Post> posts = pagePost.getContent();
		List<PostDTO> postsDTO = posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class) ).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postsDTO);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setIsLastPage(pagePost.isLast());
		return postResponse;	
	}
	@Override
	public PostDTO getPostByPostID(Long postID) {
		
		Post post = this.postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postID));
		return this.modelMapper.map(post, PostDTO.class);
	}

	@Override
	public List<PostDTO> getPostByUserID(Long userID) {
		User user = this.userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userID));
		List<Post> posts = this.postRepository.findByUser(user);
		List<PostDTO> postsDTO = posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class) ).collect(Collectors.toList());
		return postsDTO;
	}

	@Override
	public List<PostDTO> getPostByCategoryID(Long categoryID) {
		Category category = this.categoryRepository.findById(categoryID).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryID));
		List<Post> posts = this.postRepository.findByCategory(category);
		List<PostDTO> postsDTO = posts.stream().map(post -> this.modelMapper.map(post, PostDTO.class) ).collect(Collectors.toList());
		return postsDTO;
	}

	

	@Override
	public PostDTO updatePost(PostDTO postDTO) {
		Post post = this.postRepository.findById(postDTO.getPostID()).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postDTO.getPostID()));
		post = this.modelMapper.map(postDTO, Post.class);
		this.postRepository.save(post);
		return this.modelMapper.map(post,PostDTO.class);
	}

	@Override
	public void deletePostByPostID(Long postID) {

		this.postRepository.deleteById(this.postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postID)).getPostID());
		
	}
	//searching
	@Override
	public PostResponse searchPost(String keyword,Integer pageNumber , Integer pageSize,String sortBy,String sortDir) {
		
		Pageable  pageable = PageRequest.of(pageNumber, pageSize,Sort.by(Direction.valueOf(sortDir.toUpperCase()),sortBy));
		
		  Page<Post> searchedPosts = this.postRepository.findByPostTitleContaining(keyword, pageable);
		  List<Post> content = searchedPosts.getContent();
		  List<PostDTO> collect = content.stream().map(searchPost -> this.modelMapper.map(searchPost, PostDTO.class)).collect(Collectors.toList());
		  PostResponse postResponse = new PostResponse();
		  postResponse.setContent(collect);
		  postResponse.setPageNumber(searchedPosts.getNumber());
		  postResponse.setPageSize(searchedPosts.getSize());
		  postResponse.setTotalElements(searchedPosts.getTotalElements());
		  postResponse.setTotalPages(searchedPosts.getTotalPages());
		  postResponse.setIsLastPage(searchedPosts.isLast());
		  
		return postResponse;
	}
	@Override
	public Boolean fileUpload(MultipartFile multipartFile,Long postID) {
		Post post = this.postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postID));
		boolean upload = this.fileUpload.upload(multipartFile);
		if(upload) {
			post.setImageName(multipartFile.getOriginalFilename());
			this.postRepository.save(post);
		}
		return upload;
	}
}
