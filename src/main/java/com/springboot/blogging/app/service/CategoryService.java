package com.springboot.blogging.app.service;

import java.util.List;

import com.springboot.blogging.app.payload.CategoryDTO;

public interface CategoryService {

		public CategoryDTO createCategory(CategoryDTO categoryDTO);
		public List<CategoryDTO> getAllCategories();
		public CategoryDTO getCategoryByCategoryID(Long categoryID);
		public CategoryDTO updateCategory(CategoryDTO categoryDTO);
		public void deleteCategoryByCategoryID(Long categoryID);
}
