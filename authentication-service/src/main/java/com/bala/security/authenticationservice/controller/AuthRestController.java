package com.bala.security.authenticationservice.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bala.security.authenticationservice.dto.LoginDetails;
import com.bala.security.authenticationservice.dto.MessageResponse;
import com.bala.security.authenticationservice.dto.ResponseToken;
import com.bala.security.authenticationservice.dto.SignupRequest;
import com.bala.security.authenticationservice.entity.Access;
import com.bala.security.authenticationservice.entity.Role;
import com.bala.security.authenticationservice.entity.User;
import com.bala.security.authenticationservice.exception.CustomParameterConstraintException;
import com.bala.security.authenticationservice.jwt.JwtUtils;
import com.bala.security.authenticationservice.repository.AccessRepository;
import com.bala.security.authenticationservice.repository.RoleRepository;
import com.bala.security.authenticationservice.repository.UserRepository;
import com.bala.security.authenticationservice.service.UserDetailsImpl;



@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	AccessRepository accessRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDetails loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
						loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(new ResponseToken(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		
		// Need to move this entire logic to service layer bounded by transaction 
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Error: Username is already in use");
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Email id is already in use", null);
		}
		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()),
							 signUpRequest.getTokenValidity());
		Set<String> strRoles = signUpRequest.getRole();
		
		Set<Role> roles = new HashSet<>();
		// Below condition already now handled in constrains, can be removed
		if (strRoles == null) {
			 new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Role must not be empty", null);
		} 
		else {
			strRoles.forEach(strRole -> {
				System.out.println(" Role are : "+strRole);
				Role role = roleRepository.findByName(strRole)
						.orElseThrow(() ->  new ResponseStatusException(
						           HttpStatus.NOT_FOUND, "Role is not found. Send a valid role", null));
				roles.add(role);
			});
		}
		user.setRoles(roles);
		User savedUser=userRepository.save(user);
		Access access=new Access();
		access.setUser(savedUser);
		accessRepository.save(access);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}