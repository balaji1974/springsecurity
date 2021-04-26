package com.bala.microservice.customloginservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bala.microservice.customloginservice.model.User;



public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
