package com.newmonopoly;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ObjectDAO extends CrudRepository<Player, Long> {
	List<Player> findByUsername(String username);
}