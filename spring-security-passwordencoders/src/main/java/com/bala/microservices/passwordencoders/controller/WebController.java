package com.bala.microservices.passwordencoders.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebController {
	@GetMapping("/web/hello") 
	public String helloWorld() {
		return "hello";
	}
}
