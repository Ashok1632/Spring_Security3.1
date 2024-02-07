package com.ashok.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashok.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
		
	
	Optional<User> findByEmail(String email);
}
