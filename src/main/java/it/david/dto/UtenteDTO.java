package it.david.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UtenteDTO {

	private Long id;
	@NotBlank(message = "L'username non può essere vuoto")
	@Size(min = 1, max = 20, message = "L'username è compreso tra 1 e 20 caratteri")
	private String username;
	@Email(flags = Pattern.Flag.CASE_INSENSITIVE ) //, regexp =  con il parametro REGEXP si può definire quali caratteri sono accettati
	private String email;
	
	// Permette di ricevere soltanto la password da registrazione/login
	// ma impedisce l'invio nei JSON di risposta.
	//@Pattern
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)       //@Pattern è l'alternativa a parametri che non hanno Annotation dedicata
	private String password;
	@NotBlank(message = "La password è obbligatoria")
	@Size(min = 6 , max = 20, message = "La password è compresa tra 6 e 20 caratteri") 
	
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

	public UtenteDTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof UtenteDTO))
			return false;
		UtenteDTO other = (UtenteDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return " id: " + id + " username: " + username;
	}
	
	
	
}
