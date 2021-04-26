package com.bala.microservice.customloginservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public boolean login(String username, String password){
		UsernamePasswordAuthenticationToken token=null;
		try {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			token=new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
			authenticationManager.authenticate(token);
		}
		catch(Exception e) {
			e.printStackTrace(); // Send this error back to the user in production and do not consume it 
			return false;
		}
		
		
		boolean result=token.isAuthenticated();
		if(result)
			SecurityContextHolder.getContext().setAuthentication(token);
		return result;
	}

}
