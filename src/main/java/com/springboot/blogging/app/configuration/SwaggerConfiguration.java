package com.springboot.blogging.app.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
	
		private static final String AUTHORIZATION_HEADER = "Authorization";
		
		private ApiKey apiKey() {
				
				return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
		}
		private List<SecurityContext> securityContexts(){
			
			return Arrays.asList(SecurityContext.builder().securityReferences(this.securityReferences()).build());
		}
	
		private List<SecurityReference> securityReferences(){
			
			AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
			return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {authorizationScope}));
		}
		@Bean
		public Docket docketAPI()
		{
			return new Docket(DocumentationType.SWAGGER_2)
							 .apiInfo(this.getApiInfo())
							 .securityContexts(this.securityContexts())
							 .securitySchemes(Arrays.asList(this.apiKey()))
							 .select()
							 .apis(RequestHandlerSelectors.any())
							 .paths(PathSelectors.any())
							 .build();
		}

		private ApiInfo getApiInfo() {
			
		return	new ApiInfo(
				"Blogging Application",
				"Backend Service By : Adnan Ghori",
				"1.0", "termsofserviceurl",
				new Contact("Adnan Ghori", 
						"https:www.github.com/adnanghori",
						"adnanghori12@gmail.com"), 
				"Licence Of API's", "Licence URL ", 
				Collections.emptyList()
				);
		}
		
}
