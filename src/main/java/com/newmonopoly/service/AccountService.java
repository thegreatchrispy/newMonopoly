package com.newmonopoly.service;

import com.newmonopoly.model.Account;

public interface AccountService {
	public Account findAccountByEmail(String email);
	public Account findAccountByUsername(String username);
	public void saveAccount(Account account);
}
