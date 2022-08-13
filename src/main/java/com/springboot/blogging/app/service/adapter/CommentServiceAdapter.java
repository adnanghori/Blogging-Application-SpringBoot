package com.springboot.blogging.app.service.adapter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blogging.app.exception.ResourceNotFoundException;
import com.springboot.blogging.app.model.Comment;
import com.springboot.blogging.app.model.Post;
import com.springboot.blogging.app.payload.CommentDTO;
import com.springboot.blogging.app.repository.CommentRepository;
import com.springboot.blogging.app.repository.PostRepository;
import com.springboot.blogging.app.service.CommentService;
@Service
public class CommentServiceAdapter  implements CommentService{
		@Autowired
		private PostRepository postRepository;
		@Autowired
		private CommentRepository commentRepository;
		@Autowired
		private ModelMapper modelMapper;
	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Long postID) {
			Post post = this.postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postID));
			Comment comment = this.modelMapper.map(commentDTO, Comment.class);
			comment.setPost(post);
			Comment savedComment = this.commentRepository.save(comment);
		return this.modelMapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Long commentID) {
		
		this.commentRepository.deleteById(this.commentRepository.findById(commentID).orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentID)).getCommentID());
	}

}
