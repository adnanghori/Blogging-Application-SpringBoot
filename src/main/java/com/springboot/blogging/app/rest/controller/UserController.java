package com.springboot.blogging.app.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blogging.app.payload.UserDTO;
import com.springboot.blogging.app.service.UserService;
import com.springboot.blogging.app.utility.API_Response;

@RestController
@RequestMapping("/api/users")
public class UserController {
		@Autowired
		private UserService userService;

		
		@PostMapping("/")
		public ResponseEntity<UserDTO> createUser(@Valid  @RequestBody UserDTO userDTO){
			
			UserDTO createdUser = this.userService.createUser(userDTO);
			return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
		}
		@PreAuthorize(value = "hasRole('ADMIN')")
		@PostMapping("/admin/")
		public ResponseEntity<UserDTO> createAdminUser(@Valid  @RequestBody UserDTO userDTO){
			
			UserDTO createdUser = this.userService.createAdminUser(userDTO);
			return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
		}
		
	
		@GetMapping("/{userID}")
		public ResponseEntity<UserDTO> getUserByIUserID(@PathVariable Long userID){
			UserDTO user = this.userService.getUserByUserID(userID);
			return new ResponseEntity<UserDTO>(user,HttpStatus.FOUND);
		}
		@GetMapping("/")
		public ResponseEntity<List<UserDTO>> getAllUsers(){
			List<UserDTO> allUsers = this.userService.getAllUsers();
			return new ResponseEntity<>(allUsers,HttpStatus.FOUND);
		}
		@PutMapping("/")
		public ResponseEntity<UserDTO> updateUser( @RequestBody UserDTO userDTO){
			System.out.println("Got");
			UserDTO updateUser = this.userService.updateUser(userDTO);
			return new ResponseEntity<UserDTO>(updateUser,HttpStatus.OK);
		}

		@PreAuthorize("hasRole('ADMIN')")
		@DeleteMapping("/{userID}")
		public ResponseEntity<API_Response> deleteUserByUserID(@PathVariable Long userID){
			this.userService.deleteUserByUserID(userID);
			//return new ResponseEntity<>(Map.of("message","Deleted"),HttpStatus.OK);
			return new ResponseEntity<API_Response>(new API_Response("Deleted", true),HttpStatus.OK);
		}
}
