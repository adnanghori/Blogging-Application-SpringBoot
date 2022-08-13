package com.springboot.blogging.app.exception;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ResourceNotFoundException extends RuntimeException {
	private String resourceName;
	private String resourceFieldName;
	private Long resourceFieldValue;
	
	public ResourceNotFoundException(String resourceName, String resourceFieldName, Long resourceFieldValue) {
		super(String.format("%s Not Found with %s : %s", resourceName,resourceFieldName,resourceFieldValue));
		this.resourceName = resourceName;
		this.resourceFieldName = resourceFieldName;
		this.resourceFieldValue = resourceFieldValue;
	}
}
