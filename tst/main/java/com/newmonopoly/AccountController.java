package com.newmonopoly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newmonopoly.Account;
import com.newmonopoly.ObjectDAO;

@RestController
public class AccountController {
	@Autowired
	ObjectDAO dao;

	@RequestMapping("/createaccount")
	public String save(@RequestParam("email") String email, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("isadmin") boolean isAdmin) {
		dao.save(new Account(email, username, password, isAdmin));
		return "Account created.";
	}

	@RequestMapping("/getallaccounts")
	public String findAll() {
		String result = "";

		for (Account account : dao.findAll()) {
			result += account.toString() + "<br>";
		}

		return result;
	}

	@RequestMapping("/getaccountbyid")
	public String findById(@RequestParam("id") long id) {
		String result = "";
		result = dao.findById(id).toString();
		return result;
	}

	@RequestMapping("/getaccountbyusername")
	public String findByUsername(@RequestParam("username") String username) {
		String result = "";

		for (Account account : dao.findByUsername(username)) {
			result += account.toString() + "<br>";
		}

		return result;
	}

	@RequestMapping("/deleteaccountbyid")
	public String deleteById(@RequestParam("id") long id) {
		dao.deleteById(id);
		return "Account deleted.";
	}
}