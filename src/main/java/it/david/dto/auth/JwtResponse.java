package it.david.dto.auth;

import java.util.List;

public class JwtResponse {
 
	private String token;              //Stringa Criptata
	private String type = "Bearer";    //tipo di token. "Bearer" standard moderno
	
	private String email;
	//LIST: Usata in OUTPUT perché i dati sono già stati filtrati dal Set nel DB
	private List<String> roles;
	
	
	public JwtResponse(String token, String email, List<String> roles) {
		super();
		this.token = token;
		this.email = email;
		this.roles = roles;
	}
	
	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public List<String> getRoles() {
		return roles;
	}


	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	
	
	
	
}
