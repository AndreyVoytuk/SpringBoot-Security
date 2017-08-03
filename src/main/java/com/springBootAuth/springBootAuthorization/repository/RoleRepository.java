package com.springBootAuth.springBootAuthorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springBootAuth.springBootAuthorization.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByRole(String role);
}
