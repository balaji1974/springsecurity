package com.bala.security.authenticationservice.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bala.security.authenticationservice.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String username;
	
	private String email;
	
	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	private Long tokenValidity;
	
	private Boolean enabled;
	private Boolean accountNotExpired;
	private Boolean credentialsNotExpired;
	private Boolean accountNotLocked;
	
	
	public UserDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities, Long tokenValidity,
			Boolean enabled, Boolean accountNotExpired, Boolean credentialsNotExpired,
			Boolean accountNotLocked) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.tokenValidity=tokenValidity;
		this.enabled=enabled;
		this.accountNotExpired=accountNotExpired;
		this.credentialsNotExpired=credentialsNotExpired;
		this.accountNotLocked=accountNotLocked;
		
	}
	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
		
		return new UserDetailsImpl(
				user.getId(), 
				user.getUsername(), 
				user.getEmail(),
				user.getPassword(), 
				authorities,
				user.getTokenValidity(),
				user.getAccess().isEnabled(),
				user.getAccess().isAccountNonExpired(),
				user.getAccess().isCredentialsNonExpired(),
				user.getAccess().isAccountNonLocked()
				);
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public Long getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		if(!accountNotExpired) return false;
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		if(!accountNotLocked) return false;
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		if(!credentialsNotExpired) return false;
		return true;
	}
	@Override
	public boolean isEnabled() {
		if(!enabled) return false;
		return true;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
	public Long getTokenValidity() {
		return tokenValidity;
	}
}