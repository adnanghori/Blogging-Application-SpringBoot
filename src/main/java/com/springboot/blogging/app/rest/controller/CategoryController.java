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
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blogging.app.payload.CategoryDTO;
import com.springboot.blogging.app.service.CategoryService;
import com.springboot.blogging.app.utility.API_Response;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
		@Autowired
		private CategoryService categoryService;
		
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
		CategoryDTO createCategory = this.categoryService.createCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(createCategory,HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAllCategories(){
		List<CategoryDTO> allCategories = this.categoryService.getAllCategories();
		return new  ResponseEntity<List<CategoryDTO>>(allCategories,HttpStatus.FOUND);
	}
	@GetMapping("/{categoryID}")
	public ResponseEntity<CategoryDTO> getCategoryByCategoryID(@PathVariable Long categoryID){
		CategoryDTO category = this.categoryService.getCategoryByCategoryID(categoryID);
		return new ResponseEntity<CategoryDTO>(category,HttpStatus.FOUND);
	}
	@PutMapping("/")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO){
		CategoryDTO updatedCategory = this.categoryService.updateCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(updatedCategory,HttpStatus.OK);
	}
	@DeleteMapping("/{categoryID}")
	public ResponseEntity<API_Response> deleteCategory(@PathVariable Long categoryID){
		this.categoryService.deleteCategoryByCategoryID(categoryID);
		return new ResponseEntity<API_Response>(new API_Response("Deleted",true),HttpStatus.OK);
	}
}
