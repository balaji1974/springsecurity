package com.bala.microservice.customloginservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bala.microservice.customloginservice.model.Role;



public interface RoleRepository extends JpaRepository<Role, Long> {
	
}
