package com.bala.microservices.databaseauthenticationprovider.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bala.microservices.databaseauthenticationprovider.filter.ApiSecurityFilter;
import com.bala.microservices.databaseauthenticationprovider.security.CustomAuthenticaionProvider;

@Configuration
@Order(1)
public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	CustomAuthenticaionProvider customAuthenticationProvider;
	
	@Autowired
	InMemoryUserDetailsManager inMemoryUserDetailsManager;

	// Configure custom authentication provider
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		
		// custom authentication - can be used for database or LDAP authentication later 
		authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
		
		//1st way of In-memory authentication
		authenticationManagerBuilder.inMemoryAuthentication().withUser("b1").password(passwordEncoder.encode("b1")).roles("ADMIN");
		
		// 2nd way of In-Memory authentication
		authenticationManagerBuilder.userDetailsService(inMemoryUserDetailsManager).passwordEncoder(passwordEncoder);
	}

	
	// Configure custom authentication manager
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.antMatcher("/api/**").authorizeRequests().anyRequest().hasRole("ADMIN").and().httpBasic();   //.and().csrf().disable(); //not needed
		httpSecurity.addFilterBefore(new ApiSecurityFilter(), BasicAuthenticationFilter.class);
	}
	
	

}
