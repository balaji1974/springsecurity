package com.bala.microservices.formbasedsecurity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.bala.microservices.formbasedsecurity.security.CustomAuthenticaionProvider;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	CustomAuthenticaionProvider customAuthenticationProvider;

	// Configure custom authentication provider
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		
		// custom authentication - can be used for database or LDAP authentication later 
		authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
		
		// 1st way of In-memory authentication
		authenticationManagerBuilder.inMemoryAuthentication().withUser("b1").password(passwordEncoder.encode("b1")).roles("USER");
		
		// 2nd way of In-Memory authentication
		authenticationManagerBuilder.userDetailsService(getInMemoryUserDetailsManager()).passwordEncoder(passwordEncoder);
	}

	
	// Configure custom authentication manager
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.formLogin();
		httpSecurity.authorizeRequests().anyRequest().authenticated();
	}
	
	@Bean
	public  BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private InMemoryUserDetailsManager getInMemoryUserDetailsManager() {
		InMemoryUserDetailsManager inMemoryUserDetailsManager=new InMemoryUserDetailsManager();
		UserDetails user1=User.withUsername("user").password(passwordEncoder.encode("user")).authorities("READ").build();
		UserDetails user2=User.withUsername("test").password(passwordEncoder.encode("test")).authorities("READ").build();
		inMemoryUserDetailsManager.createUser(user1);
		inMemoryUserDetailsManager.createUser(user2);
		return inMemoryUserDetailsManager;
	}

}
