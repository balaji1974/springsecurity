package com.bala.security.authenticationservice.exception;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class CustomErrorResponse {

    private HttpStatus status;
    private String message;
    private List<String> errors;
    
    Logger logger = LoggerFactory.getLogger(CustomErrorResponse.class);

    public CustomErrorResponse(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        logger.error("The error from the request is :"+message);
        this.message = "The full stack trace has been logged"; // do not send the error message stack trace to consumer
        this.errors = errors;
    }

    public CustomErrorResponse(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        logger.error("The error from the request is :"+message);
        this.message = "The full stack trace has been logged"; // do not send the error message stack trace to consumer
        errors = Arrays.asList(error);
    }

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getErrors() {
		return errors;
	}
    
    
}
