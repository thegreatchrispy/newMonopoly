package com.newmonopoly;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ObjectDAO extends CrudRepository<Account, Long> {
	List<Account> findByUsername(String username);
}