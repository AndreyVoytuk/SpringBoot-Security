package com.springBootAuth.springBootAuthorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springBootAuth.springBootAuthorization.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
