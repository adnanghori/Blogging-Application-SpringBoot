package com.springboot.blogging.app.utility;

import java.util.List;

import com.springboot.blogging.app.payload.PostDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor @NoArgsConstructor
public class PostResponse {
	
		private List<PostDTO> content;
		private Integer pageNumber;
		private Integer pageSize;
		private Long totalElements;
		private Integer totalPages;
		private Boolean isLastPage;
		
}
