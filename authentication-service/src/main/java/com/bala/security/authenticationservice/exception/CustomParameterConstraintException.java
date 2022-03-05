package com.bala.security.authenticationservice.exception;

public class CustomParameterConstraintException extends RuntimeException {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomParameterConstraintException() {
        super();
    }

    public CustomParameterConstraintException(String message) {
        super(message);
    }
}