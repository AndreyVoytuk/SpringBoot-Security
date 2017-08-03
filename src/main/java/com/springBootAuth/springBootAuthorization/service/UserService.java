package com.springBootAuth.springBootAuthorization.service;

import com.springBootAuth.springBootAuthorization.model.User;

public interface UserService {

	public User findByUserEmail(String email);

	public void saveUser(User user);

}
