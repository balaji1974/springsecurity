package com.bala.microservices.databaseauthenticationprovider.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;


public class ApiSecurityFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("This is before the api security filter is called");
		chain.doFilter(request, response);
		System.out.println("This is after the api security filter is called");
		
	}
}
