package com.ashok.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashok.entity.Role;

public interface RoleRepo  extends JpaRepository<Role, Integer>{

}
