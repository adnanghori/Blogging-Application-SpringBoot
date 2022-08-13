package com.springboot.blogging.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BloggingAppApplication /*implements CommandLineRunner*/{
	
//		@Autowired
//		private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BloggingAppApplication.class, args);
//		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
//		String encode = b.encode("12345");
//		System.out.println(encode);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
//	@Override
//	public void run(String... args) throws Exception {
//		
//			Role role = new Role();
//			role.setRoleID(AppConstants.ROLE_NORMAL);
//			role.setRole("ROLE_ADMIN");
//			this.roleRepository.save(role);
//	}
//	
//	
}
