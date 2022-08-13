package com.springboot.blogging.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blogging.app.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	

}
