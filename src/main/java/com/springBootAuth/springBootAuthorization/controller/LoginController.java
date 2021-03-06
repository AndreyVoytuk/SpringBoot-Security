package com.springBootAuth.springBootAuthorization.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springBootAuth.springBootAuthorization.model.User;
import com.springBootAuth.springBootAuthorization.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@GetMapping(value = {"/", "/login"})
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@GetMapping(value = "/registration")
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@PostMapping(value = "/registration")
	public ModelAndView createNewUser(@Valid User user,
			BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExist = userService.findByUserEmail(user.getEmail());

		if (userExist != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage",
					"User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("home");
		}

		return modelAndView;
	}

	@GetMapping(value = "/home")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		User user = userService.findByUserEmail(authentication.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " "
				+ user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage",
				"Content Available Only for Users with Admin Role");
		modelAndView.setViewName("home");
		return modelAndView;
	}
}
