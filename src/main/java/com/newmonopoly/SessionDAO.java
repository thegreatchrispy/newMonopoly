package com.newmonopoly;

import org.springframework.data.repository.CrudRepository;

public interface SessionDAO extends CrudRepository<Session, Long> {
}