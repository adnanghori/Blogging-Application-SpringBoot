package com.springboot.blogging.app.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blogging.app.configuration.security.JwtUtil;
import com.springboot.blogging.app.service.adapter.UserDetailServiceAdapter;
import com.springboot.blogging.app.utility.API_Response;
import com.springboot.blogging.app.utility.JwtRequest;
import com.springboot.blogging.app.utility.JwtResponse;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailServiceAdapter userDetailServiceAdapter;
	@Autowired
	private JwtUtil jwtUtil;
//	@Autowired
//	private UserServiceAdapter userServiceAdapter;
//	
	
	 private void authenticate(String username , String password) {
		 try {
			 this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		 }catch (DisabledException de) {
			// TODO: handle exception
			 throw new DisabledException("User Disabled"+de.getMessage());
		}      
		 catch (BadCredentialsException bce) {
			// TODO: handle exception
			 throw new BadCredentialsException("Invalid Credentials"+bce.getMessage());
			 
		}
	 }
	 @PostMapping("/generate-token")
	 public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest){
		 
		 try {
			 authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
			 UserDetails userDetails = this.userDetailServiceAdapter.loadUserByUsername(jwtRequest.getUsername());		 
			 String token = this.jwtUtil.generateToken(userDetails); 
			 return new ResponseEntity<JwtResponse>(new JwtResponse(token),HttpStatus.OK);
		} catch (UsernameNotFoundException e) {
			// TODO: handle exception
				
			return new ResponseEntity<API_Response>(new API_Response("User Not Found",false),HttpStatus.NOT_FOUND);
			
		} 
		 catch (BadCredentialsException bce) {
				// TODO: handle exception
				 return new ResponseEntity<API_Response>(new API_Response("Invalid Credentials",false),HttpStatus.UNAUTHORIZED);	 
			}
		
	 }
//	 @GetMapping("/current-user")
//	 public User getCurrentUser(Principal principal) {
//		 
//		 return this.userServiceAdapter.getUser(principal.getName());
//		
//	 }
}
