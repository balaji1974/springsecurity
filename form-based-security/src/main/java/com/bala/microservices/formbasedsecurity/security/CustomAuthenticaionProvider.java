package com.bala.microservices.formbasedsecurity.security;

import java.util.Arrays;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticaionProvider implements AuthenticationProvider{

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username=authentication.getName();
		String password=authentication.getCredentials().toString();
		if(username.equals("balaji") && password.equals("balaji")) {
			return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList());	
		}
		else {
			throw new BadCredentialsException("Invalid Username and password");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	

}
