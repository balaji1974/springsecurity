package com.bala.microservices.databaseauthenticationprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bala.microservices.databaseauthenticationprovider.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
