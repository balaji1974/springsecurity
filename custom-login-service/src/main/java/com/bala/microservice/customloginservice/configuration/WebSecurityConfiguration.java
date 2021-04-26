package com.bala.microservice.customloginservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bala.microservice.customloginservice.service.UserDetailsServiceImpl;


@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	// Configure custom authentication provider
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		// database authentication 
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		
		//System.out.println(passwordEncoder.encode("bala"));
		//System.out.println(passwordEncoder.encode("nizar"));
	}

	
	// Configure custom authentication manager
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers("/web/**","/main").hasAnyRole("ADMIN","USER")//.authenticated()
			.antMatchers("/h2-console/**","/","/login").permitAll() // Not authenticated and do not do this in production and also do not use H2 anyway :) 
			.anyRequest().denyAll() // add this in the last WebSecurityConfigurerAdapter so that any other request will not go through 
			//.and().formLogin()
			;
		
		// These 2 lines are only needed for h2-console and can be disabled if h2-console is no needed
		httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
	}
	
	@Bean
	AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}
	
}
