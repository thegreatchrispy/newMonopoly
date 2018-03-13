package com.newmonopoly;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newmonopoly.Player;
import com.newmonopoly.ObjectDAO;

@RestController
public class PlayerController {
	@Autowired
	ObjectDAO dao;

	@RequestMapping("/createplayer")
	public String save(@RequestParam("email") String email, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("isadmin") boolean isAdmin) {
		dao.save(new Player(email, username, password, isAdmin));
		return "Player created.";
	}

	@RequestMapping("/getallplayers")
	public String findAll() {
		String result = "";

		for (Player player : dao.findAll()) {
			result += player.toString() + "<br>";
		}

		return result;
	}

	@RequestMapping("/getplayerbyid")
	public String findById(@RequestParam("id") long id) {
		String result = "";
		result = dao.findById(id).toString();
		return result;
	}

	@RequestMapping("/getplayerbyusername")
	public String findByUsername(@RequestParam("username") String username) {
		String result = "";

		for (Player player : dao.findByUsername(username)) {
			result += player.toString() + "<br>";
		}

		return result;
	}

	@RequestMapping("/deleteplayerbyid")
	public String deleteById(@RequestParam("id") long id) {
		dao.deleteById(id);
		return "Player deleted.";
	}
}