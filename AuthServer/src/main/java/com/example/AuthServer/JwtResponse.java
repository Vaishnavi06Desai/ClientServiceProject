package com.example.AuthServer;

public class JwtResponse {

	private String jwttoken;

	public void setToken(String token){
		this.jwttoken = token;
	}
	public String getToken() {
		return this.jwttoken;
	}
    
}