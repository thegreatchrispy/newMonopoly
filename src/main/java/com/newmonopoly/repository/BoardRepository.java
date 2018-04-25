package com.newmonopoly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newmonopoly.model.Board;

@Repository("boardRepository")
public interface BoardRepository extends JpaRepository<Board, Long> {
	Board findById(int id);
}