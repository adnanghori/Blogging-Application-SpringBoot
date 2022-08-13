package com.springboot.blogging.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class Role {
	@Id
	private Integer roleID;
	private String role;
		
	
}
