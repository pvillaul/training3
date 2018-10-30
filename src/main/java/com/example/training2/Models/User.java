package com.example.training2.Models;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.FetchType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User {
	@Id
	private String nombre;
	private String contraseña;
	@ElementCollection(fetch = FetchType.EAGER)
	private List<GrantedAuthority> roles;
	
	public User () {}
	
	public User(String name, String pass, List<GrantedAuthority> roles) {
		this.nombre = name;
		this.contraseña = new BCryptPasswordEncoder().encode(pass);
		this.roles = roles;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public List<GrantedAuthority> getRoles() {
		return roles;
	}

	public void setRoles(List<GrantedAuthority> roles) {
		this.roles = roles;
	}
}
