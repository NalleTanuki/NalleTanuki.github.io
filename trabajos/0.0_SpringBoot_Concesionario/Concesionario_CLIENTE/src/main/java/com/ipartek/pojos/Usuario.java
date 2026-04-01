package com.ipartek.pojos;


public class Usuario {

	private int id;
	private String user;
	private String pass;
	private String salt;
	
	
	private Rol role;
	
	
//	CONSTRUCTORES
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
		this.role= role;
	}

//	GETTERS -SETTERS
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
	
//toSTRING
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", user=" + user + ", pass=" + pass + ", salt=" + salt + ", role=" + role + "]";
	}
	
}
