package it.david.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank(message = "Username obbligatorio")
	private String username;
	
	@NotBlank(message = "Password obbligatoria")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	public LoginRequest() {}

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
	
	
}
	
