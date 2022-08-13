package com.springboot.blogging.app.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CategoryDTO {
	
	private Long categoryID;
	@NotEmpty @Size(min = 4 , max = 50 , message = "should be more than 4 characters") 
	private String categoryTitle;
	@NotEmpty  @Size(min = 10 , message = "should be more than 10 char")
	private String categoryDescription;
	
}
