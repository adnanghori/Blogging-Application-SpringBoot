package com.springboot.blogging.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blogging.app.model.Category;
import com.springboot.blogging.app.model.Post;
import com.springboot.blogging.app.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {

		List<Post> findByUser(User user);
		List<Post> findByCategory(Category category);
		Page<Post> findByPostTitleContaining(String postTitle,Pageable pageable);
 }
