package com.bala.security.authenticationservice.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.bala.security.authenticationservice.service.UserDetailsImpl;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${sec.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${sec.app.jwtExpirationMs}")
	private Long jwtExpirationMs;
	
	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		Set<String> roles = authentication.getAuthorities().stream()
			     .map(r -> r.getAuthority()).collect(Collectors.toSet());
		Long expiration=userPrincipal.getTokenValidity();
		if(expiration==0)
			expiration=jwtExpirationMs;
		
		Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
	    Instant expirationAt = issuedAt.plus(expiration, ChronoUnit.MILLIS);
	    
	   // System.out.println("issued at :"+issuedAt);
	   // System.out.println("expired at :"+expirationAt);
		
		return Jwts.builder()
				.setSubject("SET some app name") // to be replaced with param
				.setIssuer("Security Token Authority")
				.claim("name", (userPrincipal.getUsername()))
				.claim("scopes", roles )
				.setIssuedAt(Date.from(issuedAt))
				.setExpiration(Date.from(expirationAt))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("name", String.class);
	}
	
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}
}