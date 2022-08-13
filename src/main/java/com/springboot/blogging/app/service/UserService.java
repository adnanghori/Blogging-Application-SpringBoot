package com.springboot.blogging.app.service;

import java.util.List;

import com.springboot.blogging.app.payload.UserDTO;

public interface UserService {

	public UserDTO createUser(UserDTO userDTO);
	public UserDTO updateUser(UserDTO userDTO);
	public UserDTO getUserByUserID(Long userID);
	public List<UserDTO> getAllUsers();
	public void deleteUserByUserID(Long userID);
	public UserDTO createAdminUser(UserDTO userDTO);
}
