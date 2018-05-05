package com.newmonopoly.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

/**
 * Account model object.
 * 
 * <p>Model for the columns stored in the <tt>account</tt> table.
 * See {@link #Account(String, String, String)} for more information.
 */
@Entity
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "account_id")
	private int id;
	@Column(name = "email")
	private String email;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	@Transient
	private String password;
	@Column(name = "active")
	private int active;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "account_role", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	/**
	 * Default constructor.
	 */
	public Account() {

	}
	
	/**
	 * Constructor given parameters.
	 * 
	 * @param email The email address that will be used to log in to the account.
	 * @param username The username that will be used during gameplay.
	 * @param password The password that will be used to log in to the account.
	 */
	public Account (String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}

	/**
	 * Returns an integer id number that is associated with this <tt>Account</tt> object from the database.
	 * 
	 * @return the id for the <tt>Account</tt> in the database
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
