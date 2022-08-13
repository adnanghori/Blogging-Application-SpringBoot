package com.springboot.blogging.app.service.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blogging.app.exception.ResourceNotFoundException;
import com.springboot.blogging.app.model.Role;
import com.springboot.blogging.app.model.User;
import com.springboot.blogging.app.payload.UserDTO;
import com.springboot.blogging.app.repository.RoleRepository;
import com.springboot.blogging.app.repository.UserRepository;
import com.springboot.blogging.app.service.UserService;
import com.springboot.blogging.app.utility.AppConstants;
@Service
public class UserServiceAdapter implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		
		User user = this.DTO_TO_USER(userDTO);
		user.setRawPassword(userDTO.getUserPassword());
		user.setUserPassword(this.bCryptPasswordEncoder.encode(userDTO.getUserPassword()));
		Role role = this.roleRepository.findById(AppConstants.ROLE_NORMAL).get();
		user.getRole().add(role);
//		Set<Role> userRoles = new HashSet<>();
//		Role role = new Role();
//		role.setRole("ROLE_NORMAL");
//		userRoles.add(role);
//		user.setRole(userRoles);
		User savedUser = this.userRepository.save(user);
		return this.USER_TO_DTO(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO) {
		
		User user = this.userRepository.findById(this.DTO_TO_USER(userDTO).getUserID()).orElseThrow(() -> new ResourceNotFoundException("User", "ID",userDTO.getUserID()));
		user = this.DTO_TO_USER(userDTO);
		if(user.getRole().isEmpty()) {
			Role role = this.roleRepository.findById(AppConstants.ROLE_NORMAL).get();
			user.getRole().add(role);
		}
		this.userRepository.save(user);
		return this.USER_TO_DTO(user);
	}

	@Override
	public UserDTO getUserByUserID(Long userID) {
		User user = this.userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userID));
		return this.USER_TO_DTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
	//	List<UserDTO> dtos = null;
		List<User> users = this.userRepository.findAll();
		List<UserDTO> userDtos = users.stream().map(user->this.USER_TO_DTO(user)).collect(Collectors.toList());
//		for(User u : users) {
//			UserDTO userDTO = this.USER_TO_DTO(u);
//			dtos = List.of(userDTO);
//		}
		return userDtos;
	}

	@Override
	public void deleteUserByUserID(Long userID) {
		
	//	User user = this.userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userID));
		this.userRepository.deleteById(this.userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userID)).getUserID());
		
	}
	@Override
	public UserDTO createAdminUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = this.DTO_TO_USER(userDTO);
		user.setRawPassword(userDTO.getUserPassword());
		user.setUserPassword(this.bCryptPasswordEncoder.encode(userDTO.getUserPassword()));
		Role role = this.roleRepository.findById(AppConstants.ROLE_ADMIN).get();
		user.getRole().add(role);
//		Set<Role> userRoles = new HashSet<>();
//		Role role = new Role();
//		role.setRole("ROLE_ADMIN");
//		userRoles.add(role);
//		user.setRole(userRoles);
		this.userRepository.save(user);
		return this.USER_TO_DTO(user);
	}
	private User DTO_TO_USER(UserDTO userDTO) {
//		User user = new User();
//		user.setUserID(userDTO.getUserID());
//		user.setUserName(userDTO.getUserName());
//		user.setUserEmail(userDTO.getUserEmail());
//		user.setUserAbout(userDTO.getUserAbout());
//		user.setUserPassword(userDTO.getUserPassword());
		
		User user = this.modelMapper.map(userDTO, User.class);
		
		return user; 
	}
	private UserDTO USER_TO_DTO(User user) {
//		UserDTO userDTO = new UserDTO();
//		userDTO.setUserID(user.getUserID());
//		userDTO.setUserName(user.getUserName());
//		userDTO.setUserEmail(user.getUserEmail());
//		userDTO.setUserAbout(user.getUserAbout());
//		userDTO.setUserPassword(user.getUserPassword());
//		
		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
		return userDTO;
	}

	
 
}
