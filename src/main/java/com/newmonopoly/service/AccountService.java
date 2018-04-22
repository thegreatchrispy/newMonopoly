package com.newmonopoly.service;

import com.newmonopoly.model.Account;

public interface AccountService {
	public Account findUserByEmail(String email);
	public Account findUserByUsername(String username);
	public void saveAccount(Account account);
}
