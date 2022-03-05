package com.bala.security.authenticationservice.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/*
 * 
 * Code taken from https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
 * Thank you baeldung
 * 
 */

@ControllerAdvice
class RESTExceptionHandler extends ResponseEntityExceptionHandler  {

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
						  HttpRequestMethodNotSupportedException ex, 
						  HttpHeaders headers, 
						  HttpStatus status, 
						  WebRequest request) {
	    StringBuilder builder = new StringBuilder();
	    builder.append(ex.getMethod());
	    builder.append(
	      " method is not supported for this request. Supported methods are ");
	    ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

	    CustomErrorResponse errorRespone = new CustomErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, 
	      ex.getLocalizedMessage(), builder.toString());
	    return new ResponseEntity<Object>(
	    		errorRespone, new HttpHeaders(), errorRespone.getStatus());
	}
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
					      MethodArgumentNotValidException ex, 
					      HttpHeaders headers, 
					      HttpStatus status, 
					      WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        
        CustomErrorResponse errorRespone = 
          new CustomErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(
          ex, errorRespone, headers, errorRespone.getStatus(), request);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
    					ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + 
              violation.getPropertyPath() + ": " + violation.getMessage());
        }

        CustomErrorResponse errorRespone = 
          new CustomErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(
        		errorRespone, new HttpHeaders(), errorRespone.getStatus());
    }
    
    /* Can be used later for other constraints 
    @ExceptionHandler({CustomParameterConstraintException.class})
    public ResponseEntity<Object> handleCustomParameterConstraintExceptions( Exception e ) {
    	List<String> errors = new ArrayList<String>();
    	errors.add(e.getMessage());
		CustomErrorResponse errorRespone = 
				new CustomErrorResponse(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), errors);
		return new ResponseEntity<Object>(
		        errorRespone, new HttpHeaders(), errorRespone.getStatus());
       
    }
    */
    
    /*
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleMessageNotReadable(HttpMessageNotReadableException ex) {
    	String error = 
    	          ex.getMessage();
    	CustomErrorResponse errorRespone = 
                new CustomErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(
        		errorRespone, new HttpHeaders(), errorRespone.getStatus());
    }
    */
    
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
					      MissingServletRequestParameterException ex, HttpHeaders headers, 
					      HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        
        CustomErrorResponse errorRespone = 
                new CustomErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(
        		errorRespone, new HttpHeaders(), errorRespone.getStatus());
    }
    
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
    					MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error = 
          ex.getName() + " should be of type " + ex.getRequiredType().getName();

        CustomErrorResponse errorRespone = 
                new CustomErrorResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(
        		errorRespone, new HttpHeaders(), errorRespone.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
    					NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        CustomErrorResponse errorRespone = 
                new CustomErrorResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(errorRespone, new HttpHeaders(), errorRespone.getStatus());
    }
    
    /*
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
    	CustomErrorResponse errorRespone = 
                new CustomErrorResponse(
          HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<Object>(
        		errorRespone, new HttpHeaders(), errorRespone.getStatus());
    }
    */
    
}