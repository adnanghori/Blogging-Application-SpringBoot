package com.springboot.blogging.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blogging.app.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
