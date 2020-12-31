package com.cos.hello.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {
	private int id; // Primary Key, Auto_increment
	private String username;
	private String password;
	private String email;
	
	
	public Users(int id, String username, String email) {
		this.id = id;
		this.username = username;
		this.email = email;
	}	
}
