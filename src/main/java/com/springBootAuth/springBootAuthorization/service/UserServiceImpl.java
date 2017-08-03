package com.springBootAuth.springBootAuthorization.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springBootAuth.springBootAuthorization.model.Role;
import com.springBootAuth.springBootAuthorization.model.User;
import com.springBootAuth.springBootAuthorization.repository.RoleRepository;
import com.springBootAuth.springBootAuthorization.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

	private static final String ADMIN = "ADMIN";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User findByUserEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		Role role = roleRepository.findByRole(ADMIN);
		user.setRole(role);
		userRepository.save(user);

	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		List<GrantedAuthority> authorities = getUserAuthority(user.getRole());
		return buildUserForAuthentication(user, authorities);
	}

	private List<GrantedAuthority> getUserAuthority(Role userRole) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority(userRole.getRole()));

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(
				roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(User user,
			List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), user.getPassword(), user.isActive(), true,
				true, true, authorities);
	}

}
