package com.springboot.blogging.app.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostDTO {
	

	private Long postID;
	@NotEmpty @Size(min = 10 , max = 50)
	private String postTitle;
	@NotEmpty @Size(min = 50)
	private String postContent;
	private String imageName = "default.png";
	private Date addedDate = new Date();
	@NotNull
	private CategoryDTO category;
	@NotNull
	private UserDTO user;
	private List<CommentDTO> comments = new ArrayList<>();
}
  