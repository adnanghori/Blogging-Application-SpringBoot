package com.springboot.blogging.app.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.blogging.app.payload.PostDTO;
import com.springboot.blogging.app.utility.PostResponse;

public interface PostService {

		public PostDTO createPost(PostDTO postDTO);
		public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
		public PostDTO getPostByPostID(Long postID);
		public List<PostDTO> getPostByUserID(Long userID);
		public List<PostDTO> getPostByCategoryID(Long categoryID);
		public PostResponse searchPost(String keyword,Integer pageNumber , Integer pageSize,String sortBy,String sortDir);
		public PostDTO updatePost(PostDTO postDTO);
		public void deletePostByPostID(Long postID);
		public Boolean fileUpload(MultipartFile multipartFile,Long postID);
}
