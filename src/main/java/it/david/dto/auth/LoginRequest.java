package it.david.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank(message = "Email obbligatoria")
	private String email;
	
	@NotBlank(message = "Password obbligatoria")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	public LoginRequest() {}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
	
