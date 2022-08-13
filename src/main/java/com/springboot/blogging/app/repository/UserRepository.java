package com.springboot.blogging.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blogging.app.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
		Optional<User> findByUserEmail(String userEmail);
}
