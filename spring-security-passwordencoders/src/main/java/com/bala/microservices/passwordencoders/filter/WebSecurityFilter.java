package com.bala.microservices.passwordencoders.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class WebSecurityFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("This is before the web security filter is called");
		chain.doFilter(request, response);
		System.out.println("This is after the web security filter is called");
		
	}
}
