package com.springboot.blogging.app.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.springboot.blogging.app.exception.AccessDenied;
import com.springboot.blogging.app.service.adapter.UserDetailServiceAdapter;

//import com.springboot.blogging.app.service.adapter.UserDetailServiceAdapter;


//public class SpringSecurityConfiguration extends  WebSecurityConfigurerAdapter{
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		// TODO Auto-generated method stub
//		super.configure(auth);
//	}
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// TODO Auto-generated method stub
//		super.configure(http);
//	}
//	@Override 
//	public void configure(WebSecurity web) throws Exception {
//		// TODO Auto-generated method stub
//		super.configure(web);
//	}
//}
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
public class SpringSecurityConfiguration  {
	@Autowired	
	private UserDetailServiceAdapter userDetailServiceAdapter;
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Autowired
	private AccessDenied accessDenied;
	public static final String PUBLIC_URL[] = {
			"/generate-token",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
			http
			.csrf()
			.disable()
			.cors()
			.disable()
			.authorizeHttpRequests()
			.antMatchers(PUBLIC_URL).permitAll() 
			.anyRequest() 
			.authenticated()
			.and()
			.formLogin()
			.and()
			.authenticationProvider(authenticationProvider())
			.exceptionHandling()
			.authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
			.and()
			.exceptionHandling()
			.accessDeniedHandler(this.accessDenied)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
			
			return http.build();
		}

		@Bean
		protected BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
		public AuthenticationManager authenticationManager(
		        AuthenticationConfiguration authConfig) throws Exception {
		    return authConfig.getAuthenticationManager();
		}
		 
		@Bean
		public DaoAuthenticationProvider authenticationProvider() {
		    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		     authProvider.setUserDetailsService(this.userDetailServiceAdapter);
		     authProvider.setPasswordEncoder(passwordEncoder());
		     
		    return authProvider;
		}
		@Bean
		public WebSecurityCustomizer webSecurityCustomizer() {
			return (web) -> web.ignoring().antMatchers(PUBLIC_URL);
		}
	
}
