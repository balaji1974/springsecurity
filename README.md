# Spring Security  
## Basics of Security in Spring  
### Authentication Filter will call ->  Authentication Manager will call -> Authentication Provider will call -> User Detail Service and Password Encoder  
### Authentication Filter -> has the security context that has both Authentication Success Handler and Authentication Failure Handler  

## 1. Simple basic security (Project name: basic-security)  

a. To enable security in spring all we need to do is add the following dependency in our pom.xml file 
```xml  
<dependency>  
	<groupId>org.springframework.boot</groupId>  
	<artifactId>spring-boot-starter-security</artifactId>  
</dependency>  
```  

b. When we start the server we will get a "Using generated security password:". This is the autogenerated password which keeps changing on every server startup.  

c. When we access any resource from the server all we need to do is use the default user name "user" and the auto generated password when the server starts up.  

## 2. In-Memory Security (Project name: inmemory-security)   

a. The above security cannot be used for real time senarios and hence we move to our next type which is the custom in-memory security.    

b. For this we need to extend a Configuration class with WebSecurityConfigurerAdapter and then over ride the two configure methods.   

c. We also need to inject a bean for password encoder bean which is mandatory in Spring security now.  

## 3. Custom Authentication Provider (Project name: custom-authentication-security)  

a. Create a spring component class called CustomAuthenticaionProvider and implements org.springframework.security.authentication.AuthenticationProvider  

b. Override the following 2 methods:  
public Authentication authenticate(Authentication authentication) throws AuthenticationException {}  
public boolean supports(Class<?> authentication) {}  

c. Check code for details of implementation of these methods.  

## 4. Multiple Authentication Providers (Project name: multiple-authentication-security)  

a. Often we will have situations where we will have more than one way in which the user needs to be authenticated into the application. So we need to provide Spring security with multiple ways in which we can authenticate our users.  

b. This project is a combination of 2 and 3 steps described above.    
Only change is in the protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {}  method used in SecurityConfiguration file where we add our 2 authentication builders as below:    
authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);   
authenticationManagerBuilder.inMemoryAuthentication().withUser("b1").password(passwordEncoder.encode("b1")).roles("USER");    

c. The order of authentication checking will be the order in which we provide the Spring AuthenticationManagerBuilder our different methods of Authentication.   

## 5. Form based authentication (Project name: form-based-security)    
a. For form based authentication do the following in the configure method of the class extending the WebSecurityConfigurerAdapter     
httpSecurity.httpBasic() > Replace with  -> httpSecurity.formLogin();    

b. Now you will be presented with a login form automatically and once you login you will be redirected to the requested resource.   








