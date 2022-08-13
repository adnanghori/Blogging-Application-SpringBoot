package com.springboot.blogging.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blogging.app.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
