package com.newmonopoly;

import java.util.Arrays;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newmonopoly.Session;
import com.newmonopoly.SessionDAO;

@RestController
public class SessionController {
	@Autowired
	SessionDAO sdao;
	Gson gson = new Gson();

	@RequestMapping("/getsessionbyid")
	public String findById(@RequestParam("id") long id) {
		String result = "";
		result = sdao.findById(id).toString();
		return result;
	}
}