package com.bala.microservices.passwordencoders.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class InMemorySecurityConfiguration {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Bean
	public InMemoryUserDetailsManager getInMemoryUserDetailsManager() {
		InMemoryUserDetailsManager inMemoryUserDetailsManager=new InMemoryUserDetailsManager();
		UserDetails user1=User.withUsername("user").password(passwordEncoder.encode("user")).authorities("ADMIN").build();
		UserDetails user2=User.withUsername("test").password(passwordEncoder.encode("test")).authorities("ADMIN").build();
		inMemoryUserDetailsManager.createUser(user1);
		inMemoryUserDetailsManager.createUser(user2);
		return inMemoryUserDetailsManager;
	}


}
