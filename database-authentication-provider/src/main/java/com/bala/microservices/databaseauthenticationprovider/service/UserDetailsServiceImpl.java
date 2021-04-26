package com.bala.microservices.databaseauthenticationprovider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bala.microservices.databaseauthenticationprovider.model.User;
import com.bala.microservices.databaseauthenticationprovider.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("User name not found : "+username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
	}
	
}
