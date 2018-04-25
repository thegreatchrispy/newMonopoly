package com.newmonopoly.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.newmonopoly.model.Account;
import com.newmonopoly.service.AccountService;

@RestController
public class WebpageController {
	
	@Autowired
	private AccountService accountService;

	// ModelAndView
	@RequestMapping("/")
	public ModelAndView home_loggedOut(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}

	// ModelAndView
	@RequestMapping("/home")
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/index");
		return modelAndView;
	}

	// ModelAndView
	@RequestMapping("/create_join")
	public ModelAndView create_join(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/create_join");
		return modelAndView;
	}

	// ModelAndView
	@RequestMapping("/help")
	public ModelAndView help(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/help");
		return modelAndView;
	}

	// ModelAndView
	@RequestMapping("/edit_profile")
	public ModelAndView edit_profile(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/edit_profile");
		return modelAndView;
	}

	// ModelAndView
	@RequestMapping("/learn")
	public ModelAndView help_loggedOut(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("help");
		return modelAndView;
	}

	// ModelAndView
	@RequestMapping("/login")
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	// ModelAndView
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	// Create Account
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String createNewAccount(@RequestParam("email") String email, @RequestParam("username") String username, @RequestParam("password") String password) {
		Account account = new Account(email, username, password);
		//ModelAndView modelAndView = new ModelAndView();
		accountService.saveAccount(account);
		//modelAndView.setViewName("registration");
		return "Success!";
		
	}

	// Validation for Email.
	@RequestMapping(value="/checkEmail")
	public String getEmail(@RequestParam("email") String email){
		Account accountExists = accountService.findUserByEmail(email);
		if (accountExists != null) {
			return "false";
		} else {
			return "true";
		}// method return boolean if user exist or non in database.
	}

	// Validation for Username
	@RequestMapping(value="/checkUsername")
	public String getUsername(@RequestParam("username") String username){
		Account accountExists = accountService.findUserByUsername(username);
		if (accountExists != null) {
			return "false";
		} else {
			return "true";
		}
	}
//	public ModelAndView createNewAccount(@Valid Account account, BindingResult bindingResult) {
//		ModelAndView modelAndView = new ModelAndView();
//		Account accountExists = accountService.findUserByEmail(account.getEmail());
//		if (accountExists != null) {
//			bindingResult
//					.rejectValue("email", "error.account",
//							"There is already a account registered with the email provided");
//		}
//		if (bindingResult.hasErrors()) {
//			modelAndView.setViewName("registration");
//		} else {
//			accountService.saveAccount(account);
//			modelAndView.addObject("successMessage", "account has been registered successfully");
//			modelAndView.addObject("account", new Account());
//			modelAndView.setViewName("registration");
//			
//		}
//		return modelAndView;
//	}
	
	// @RequestMapping(value="/admin/home", method = RequestMethod.GET)
	// public ModelAndView test(){
	// 	ModelAndView modelAndView = new ModelAndView();
	// 	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	// 	Account account = accountService.findUserByEmail(auth.getName());
	// 	modelAndView.addObject("userName", "Welcome " + account.getUsername() + " (" + account.getEmail() + ")");
	// 	modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
	// 	modelAndView.setViewName("admin/home");
	// 	return modelAndView;
	// }
	

}
