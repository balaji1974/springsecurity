package com.bala.microservices.springsecurityfilter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MyRestController {
	@GetMapping("/api/hello") 
	public String helloWorld() {
		return "Hello World";
	}
}
