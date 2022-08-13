package com.springboot.blogging.app.service.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blogging.app.exception.ResourceNotFoundException;
import com.springboot.blogging.app.model.Category;
import com.springboot.blogging.app.payload.CategoryDTO;
import com.springboot.blogging.app.repository.CategoryRepository;
import com.springboot.blogging.app.service.CategoryService;
@Service
public class CategoryServiceAdapter implements CategoryService {
		@Autowired
		private CategoryRepository categoryRepository;
		@Autowired
		private ModelMapper modelMapper;
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		
		Category createCategory = this.modelMapper.map(categoryDTO, Category.class);
		this.categoryRepository.save(createCategory);
		return this.modelMapper.map(createCategory, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> list = this.categoryRepository.findAll();
		List<CategoryDTO> categoryDTO = list.stream().map(category->this.modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
		return categoryDTO;
	}

	@Override
	public CategoryDTO getCategoryByCategoryID(Long categoryID) {

		Category category = this.categoryRepository.findById(categoryID).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryID));
		return this.modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
		Category category = this.categoryRepository.findById(categoryDTO.getCategoryID()).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryDTO.getCategoryID()));
		category = this.modelMapper.map(categoryDTO, Category.class);
		this.categoryRepository.save(category);
		return this.modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public void deleteCategoryByCategoryID(Long categoryID) {

		this.categoryRepository.deleteById(this.categoryRepository.findById(categoryID).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryID)).getCategoryID());
	}

}
