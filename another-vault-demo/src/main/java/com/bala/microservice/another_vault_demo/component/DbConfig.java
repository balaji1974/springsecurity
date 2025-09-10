package com.bala.microservice.another_vault_demo.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DbConfig {
	@Value("${database.password}")
    private String dbPassword;
	
	@Value("${database.username}")
    private String userName;
	
	@PostConstruct
    public void init() {
        // Initialization logic after construction and dependency injection
        System.out.println("DbConfig bean initialized!");
        System.out.println(userName);
        System.out.println(dbPassword);
    }	
}
