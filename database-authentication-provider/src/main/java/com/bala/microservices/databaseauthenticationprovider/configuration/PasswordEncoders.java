package com.bala.microservices.databaseauthenticationprovider.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration 
public class PasswordEncoders {
	
	@Value("${password.encoder}")
	private String passwordEncoder;
	
	private static final String DEFAULT_PASSWORD_ENCODER="BCrypt";
	
	@Bean
	public  PasswordEncoder passwordEncoder() {
		if(passwordEncoder.equalsIgnoreCase("Pbkdf2PasswordEncoder")) 
			return new Pbkdf2PasswordEncoder(); // We can use other constructors also for more security, check doc 
		else if(passwordEncoder.equalsIgnoreCase("SCryptPasswordEncoder")) 
				return new SCryptPasswordEncoder(); // We can use other constructors also for more security, check doc 
		else if(passwordEncoder.equalsIgnoreCase("BCryptPasswordEncoder")) 
			return new BCryptPasswordEncoder(); // We can use other constructors also for more security, check doc 
		else return new DelegatingPasswordEncoder(DEFAULT_PASSWORD_ENCODER,createPasswordEncoderMap()); // this is the default password encoder that I have set
	}
	private Map<String, PasswordEncoder> createPasswordEncoderMap() {
		Map<String, PasswordEncoder> passwordEncoders=new HashMap<>(); 
		passwordEncoders.put("BCrypt", new BCryptPasswordEncoder()); // We can use other constructors also for more security, check doc 
		passwordEncoders.put("Pbkdf2", new Pbkdf2PasswordEncoder()); // We can use other constructors also for more security, check doc 
		passwordEncoders.put("SCrypt", new SCryptPasswordEncoder()); // We can use other constructors also for more security, check doc 
		return passwordEncoders;
	}
}
