package com.springboot.blogging.app.payload;

import lombok.Data;

@Data
public class CommentDTO {

	private Long commentID;
	private String comment;
	
}
