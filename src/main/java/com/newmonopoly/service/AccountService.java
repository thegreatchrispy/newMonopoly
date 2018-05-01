package com.newmonopoly.service;

import com.newmonopoly.model.Account;

/**
 * Service which declares functions to be used from the <tt>AccountRepository</tt> give additional access to 
 * the <tt>Account</tt> database model.
 * 
 */
public interface AccountService {
	public Account findAccountByEmail(String email);
	public Account findAccountByUsername(String username);
	public void saveAccount(Account account);
}
