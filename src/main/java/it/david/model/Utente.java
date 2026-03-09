package it.david.model;

public class Utente {

	private Long id;
	
	private String username;
	private String email;
	
	public Utente(){}

	public Utente(String username, String email) {
		super();
		this.username = username;
		this.email = email;
	}

	public String getUsername() {
		return username;
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

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return " id utente: " + id + " username: " + username + " email: " + email;
	}
	
	
	
}
