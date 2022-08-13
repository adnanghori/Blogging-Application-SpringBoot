package com.springboot.blogging.app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
@Data
@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long postID;
	private String postTitle;
	private String postContent;
	private String imageName;
	@Temporal(TemporalType.DATE)
	@CreatedDate
	private Date addedDate;
	@ManyToOne
	private Category category;
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private List<Comment> comments= new ArrayList<>(); 
}
