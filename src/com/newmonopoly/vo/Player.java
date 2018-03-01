package com.newmonopoly.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Player class.
 */
@Entity
@Table(name = "players")
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "email")
	private String email;
	@Column(name = "isAdmin")
	private boolean isAdmin;

	/**
	 * No argument constructor for Player objects.
	 */
	public Player() {
		id = -1;
	}

	/**
	 * Constructor for Player objects
	 * 
	 */
	public Player(String u, String p, String e, boolean a) {
		this();
		username = u;
		password = p;
		email = e;
		isAdmin = a;
	}

	/**
	 * Return the primary key database id of a Player object
	 * 
	 * @return id of a Player object
	 */
	public int getId() {
		return id;
	}

	public void setId(int i) {
		id = i;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String u) {
		username = u;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String p) {
		password = p;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String e) {
		email = e;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean a) {
		isAdmin = a;
	}
}