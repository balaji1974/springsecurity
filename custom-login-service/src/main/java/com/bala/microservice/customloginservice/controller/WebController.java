package com.bala.microservice.customloginservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.bala.microservice.customloginservice.service.SecurityService;


@Controller
public class WebController {
	
	@Autowired
	SecurityService securityService;
	
	@GetMapping("/web/hello") 
	public String helloWorld() {
		return "hello";
	}
	
	@GetMapping("/") 
	public String showLogin() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(String username, String password) {
		boolean result = securityService.login(username, password);
		if(result) {
			return "main";
		}
		else return "login";
	}
	
	
	
	
}
