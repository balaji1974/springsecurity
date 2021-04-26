package com.bala.microservices.databaseauthenticationprovider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bala.microservices.databaseauthenticationprovider.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
}
