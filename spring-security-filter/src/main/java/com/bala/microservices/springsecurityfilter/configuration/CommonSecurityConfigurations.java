package com.bala.microservices.springsecurityfilter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class CommonSecurityConfigurations {
	
	@Bean
	public  BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public InMemoryUserDetailsManager getInMemoryUserDetailsManager() {
		InMemoryUserDetailsManager inMemoryUserDetailsManager=new InMemoryUserDetailsManager();
		UserDetails user1=User.withUsername("user").password(bcryptPasswordEncoder().encode("user")).authorities("ADMIN").build();
		UserDetails user2=User.withUsername("test").password(bcryptPasswordEncoder().encode("test")).authorities("ADMIN").build();
		inMemoryUserDetailsManager.createUser(user1);
		inMemoryUserDetailsManager.createUser(user2);
		return inMemoryUserDetailsManager;
	}


}
