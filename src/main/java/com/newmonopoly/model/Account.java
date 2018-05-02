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
	 * @param email The email address used to log into the account.
	 * @param username The username used during gameplay.
	 * @param password The password used to log in to the account.
	 */
	public Account (String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}

	/**
	 * Returns an integer id number that is associated with the <tt>Account</tt> object from the database.
	 * 
	 * @return the id for the <tt>Account</tt> in the database
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id of the <tt>Account</tt> in the database.
	 * 
	 * @param id The new id for the <tt>Account</tt> in the database
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns a string username associated with the <tt>Account</tt>.
	 * This is what will be displayed as the Player's name during game play.
	 * 
	 * @return the username for the <tt>Account</tt>
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of the <tt>Account</tt> in the database.
	 * 
	 * @param username The new username for the <tt>Account</tt>
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns a string password that is associated with the <tt>Account</tt>.
	 * The string returned is an encrypted version of the original password.
	 * This is to prevent anyone from having access to another person's account.
	 * 
	 * @return the encrypted password for the <tt>Account</tt>
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the <tt>Account</tt> in the database.
	 * 
	 * @param password The new password for the <tt>Account</tt>
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns a string email that is associated with <tt>Account</tt>.
	 * This is what the user will use to log in.
	 * 
	 * @return the email for the <tt>Account</tt>
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email of the <tt>Account</tt> in the database.
	 * 
	 * @param email The new email for the <tt>Account</tt>
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns an integer that is either a 1 or a 0 indicating whether or not the <tt>Account</tt> is active.
	 * 
	 * @return the active status for the <tt>Account</tt>
	 */
	public int getActive() {
		return active;
	}

	/**
	 * Sets the active status of the <tt>Account</tt> in the database.
	 * 
	 * @param active The new active status for the <tt>Account</tt>
	 */
	public void setActive(int active) {
		this.active = active;
	}

	/**
	 * Returns a set of roles associated with the <tt>Account</tt>.
	 * 
	 * @return the set of roles associated with the <tt>Account</tt>
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles associated with the <tt>Account</tt> in the database.
	 * 
	 * @param roles The new set of roles for the <tt>Account</tt>
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
