package com.springboot.blogging.app.service.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.blogging.app.repository.UserRepository;
@Service
public class UserDetailServiceAdapter  implements UserDetailsService{
		
		@Autowired
		private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return this.userRepository.findByUserEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found With : "+username));
		
	}

}
