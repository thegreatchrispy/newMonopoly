package com.newmonopoly;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.io.Serializable;

@Entity
@Table(name="players")
public class Player implements Serializable {

	private static final long serialVersionUID = -3009157732242241606L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "email")
	private String email;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "is_admin")
	private boolean isAdmin;

	protected Player() {
	}

	public Player(String email, String username, String password, boolean isAdmin) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return String.format("Customer{id=%d, email=%s, username=%s, password=%s, is_admin=%b}", id, email, username, password, isAdmin);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}