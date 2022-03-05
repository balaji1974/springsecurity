package com.bala.security.authenticationservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bala.security.authenticationservice.entity.Access;
import com.bala.security.authenticationservice.entity.User;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long> {
}