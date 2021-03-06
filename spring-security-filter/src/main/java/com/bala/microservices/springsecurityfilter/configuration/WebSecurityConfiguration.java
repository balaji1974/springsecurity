package com.bala.microservices.springsecurityfilter.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bala.microservices.springsecurityfilter.filter.WebSecurityFilter;
import com.bala.microservices.springsecurityfilter.security.CustomAuthenticaionProvider;



@Configuration
@Order(2)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	
	
	@Autowired
	CustomAuthenticaionProvider customAuthenticationProvider;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
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
		httpSecurity.authorizeRequests().antMatchers("/web/**").authenticated()
			.anyRequest().denyAll() // add this in the last WebSecurityConfigurerAdapter so that any other request will not go through 
			.and().formLogin();
		httpSecurity.addFilterBefore(new WebSecurityFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	
}
