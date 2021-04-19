# Spring Security  

## Simple jar security (Project name: jar-security)  

a. To enable security in spring all we need to do is add the following dependency in our pom.xml file 
```xml  
<dependency>  
	<groupId>org.springframework.boot</groupId>  
	<artifactId>spring-boot-starter-security</artifactId>  
</dependency>  
```  

b. When we start the server we will get a "Using generated security password:"  

c. When we access any resource from the server all we need to do is use the default user name "user" and the auto generated password when the server starts up.  

