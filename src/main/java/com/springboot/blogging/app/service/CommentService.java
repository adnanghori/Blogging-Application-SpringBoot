package com.springboot.blogging.app.service;

import com.springboot.blogging.app.payload.CommentDTO;

public interface CommentService {

		public CommentDTO createComment(CommentDTO commentDTO,Long postID);
		public void deleteComment(Long commentID);
}
