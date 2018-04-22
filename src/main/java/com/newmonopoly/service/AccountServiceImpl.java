package com.newmonopoly.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.newmonopoly.model.Role;
import com.newmonopoly.model.Account;
import com.newmonopoly.repository.RoleRepository;
import com.newmonopoly.repository.UserRepository;

@Service("accountService")
public class AccountServiceImpl implements AccountService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Account findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Account findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void saveAccount(Account account) {
		account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        account.setActive(1);
        Role accountRole = roleRepository.findByRole("ADMIN");
        account.setRoles(new HashSet<Role>(Arrays.asList(accountRole)));
		userRepository.save(account);
	}

}
