package com.springboot.blogging.app.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blogging.app.payload.CommentDTO;
import com.springboot.blogging.app.service.CommentService;
import com.springboot.blogging.app.utility.API_Response;

@RestController
@RequestMapping("api/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	@PostMapping("/post/{postID}")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO,@PathVariable Long postID){
		
			CommentDTO createComment = this.commentService.createComment(commentDTO, postID);
			return new ResponseEntity<CommentDTO>(createComment,HttpStatus.CREATED);
	} 
	@DeleteMapping("/{commentID}")
	public ResponseEntity<API_Response> deleteComment(@PathVariable Long commentID){
		this.commentService.deleteComment(commentID);
		return new ResponseEntity<API_Response>(new API_Response("Deleted", true),HttpStatus.OK);
	}
}
