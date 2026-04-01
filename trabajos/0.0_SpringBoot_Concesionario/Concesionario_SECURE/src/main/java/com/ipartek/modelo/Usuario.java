package com.ipartek.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="user", unique = true, nullable = false, length = 45)
	private String user;
	
	@Column(name="pass", nullable = false, length = 64)
	private String pass;
	
	@Column(name="salt", nullable = false, length = 64)
	private String salt;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Rol role;

	
//  CONSTRUCTORES
	public Usuario() {
		super();
		this.id = 0;
		this.user = "";
		this.pass = "";
		this.salt = "";
		this.role = new Rol();
	}
	
	public Usuario(int id, String user, String pass, String salt, Rol role) {
		super();
		this.id = id;
		this.user = user;
		this.pass = pass;
		this.salt = salt;
		this.role = role;
	}

	
	
//	GETTERS - SETTERS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Rol getRole() {
		return role;
	}

	public void setRole(Rol role) {
		this.role = role;
	}

	
//	toSTRING
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", user=" + user + ", pass=" + pass + ", salt=" + salt + ", role=" + role + "]";
	}
}
