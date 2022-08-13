package com.springboot.blogging.app.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.blogging.app.payload.PostDTO;
import com.springboot.blogging.app.service.PostService;
import com.springboot.blogging.app.utility.API_Response;
import com.springboot.blogging.app.utility.AppConstants;
import com.springboot.blogging.app.utility.PostResponse;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
		@Autowired
		private PostService postService;
		@PostMapping("/")
		public ResponseEntity<PostDTO> createUser(@Valid @RequestBody PostDTO postDTO){
				
			PostDTO createdPost = this.postService.createPost(postDTO);
			return new ResponseEntity<PostDTO>(createdPost,HttpStatus.CREATED);
		}
		@GetMapping("/")
		public ResponseEntity<PostResponse> getAllPosts(
				
				@RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber ,
				@RequestParam(value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false)Integer pageSize ,
				@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy ,
				@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir
			)
		{
			PostResponse allPosts = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
			return new ResponseEntity<PostResponse>(allPosts,HttpStatus.FOUND);
		}
		
		@GetMapping("/{postID}")
		public ResponseEntity<PostDTO> getPostByPostID(@PathVariable Long postID){
			PostDTO post = this.postService.getPostByPostID(postID);
			return new ResponseEntity<PostDTO>(post,HttpStatus.FOUND);
		}
		@GetMapping("/user/{userID}")
		public ResponseEntity<List<PostDTO>> getPostByUserID(@PathVariable Long userID){
			List<PostDTO> posts = this.postService.getPostByUserID(userID);
			return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.FOUND);
		}
		@GetMapping("/category/{categoryID}")
		public ResponseEntity<List<PostDTO>> getPostByCategoryID(@PathVariable Long categoryID){
			List<PostDTO> posts = this.postService.getPostByCategoryID(categoryID);
			return new ResponseEntity<List<PostDTO>>(posts,HttpStatus.FOUND);
		}
		@PutMapping("/")
		public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO){
			PostDTO updatedPost = this.postService.updatePost(postDTO);
			return new ResponseEntity<PostDTO>(updatedPost,HttpStatus.OK);
		}
		@DeleteMapping("/{postID}")
		public ResponseEntity<API_Response> deletePostByPostID(@PathVariable Long postID){
			this.postService.deletePostByPostID(postID);
			return new ResponseEntity<API_Response>(new API_Response("Deleted", true),HttpStatus.OK);
		}
		@GetMapping("/search/{title}")
		public ResponseEntity<?> searchByTitle(
				@PathVariable String title,
				@RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber ,
				@RequestParam(value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false)Integer pageSize ,
				@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy ,
				@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir
				)
		{
			  PostResponse searchedPosts = this.postService.searchPost(title, pageNumber ,  pageSize, sortBy, sortDir);
			  if(searchedPosts.getContent().isEmpty()) {
				  return new ResponseEntity<API_Response>(new API_Response("Keyword "+title+" Not Found In Any Of The Title", false),HttpStatus.NOT_FOUND);
			  } 
			  else {
				  return new ResponseEntity<PostResponse>(searchedPosts,HttpStatus.FOUND);
			}
			  
		}
		@PostMapping("/{postID}/upload-file")
		public ResponseEntity<API_Response> uploadFile(@PathVariable Long postID , @RequestParam("file") MultipartFile file){
			if(file.isEmpty()) {
				return new ResponseEntity<API_Response>(new API_Response("Request Must Contain File",false),HttpStatus.INTERNAL_SERVER_ERROR);
			}
			boolean fileUpload = this.postService.fileUpload(file,postID);
			if(fileUpload) {
				
				return new ResponseEntity<API_Response>(new API_Response(ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(file.getOriginalFilename()).toUriString(),fileUpload),HttpStatus.OK);
			}
			else {
				
				return new ResponseEntity<API_Response>(new API_Response("Something Went Wrong",false),HttpStatus.INTERNAL_SERVER_ERROR);			
			}
			
		}
 }
