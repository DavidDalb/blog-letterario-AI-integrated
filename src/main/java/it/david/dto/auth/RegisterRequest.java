package it.david.dto.auth;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
                                    //Dto di input
public class RegisterRequest {

	@NotBlank
	@Size(min = 3, max = 20)
	private String username;
	
	@NotBlank
	@Email
	private String email;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank
	private String password;
	
	//Set usato in INPUT per unicità ruoli
	private Set<String> ruoli;
	public RegisterRequest () {}
	
	public Set<String> getRuoli() {
		return ruoli;
	}

	public String getUsername() {
		return username;
	}
	
	public void setRuoli(Set<String> ruoli) {
		this.ruoli = ruoli;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
