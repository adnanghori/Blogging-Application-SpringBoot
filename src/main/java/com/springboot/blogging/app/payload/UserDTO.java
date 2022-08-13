package com.springboot.blogging.app.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import com.springboot.blogging.app.model.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.bytebuddy.utility.nullability.AlwaysNull;

@NoArgsConstructor
@Getter @Setter @ToString
public class UserDTO {

	private Long userID;
	@NotEmpty
	@Size(min = 4,message = "min length of username must be 4 char ")
	private String userName;
	@Email(message = "Email address not valid")
	private String userEmail;
	@NotEmpty @Size(min = 4 , max = 8 , message = "password must be min of 4 and max of 8 char  ")
	private String userPassword;
	@NotEmpty
	private String userAbout;
	private Set<RoleDTO> role = new HashSet<>();
}
