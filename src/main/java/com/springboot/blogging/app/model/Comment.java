package com.springboot.blogging.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name = "comments")
public class Comment {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long commentID;
		private String comment;
		
		@ManyToOne
		private Post post;
}
