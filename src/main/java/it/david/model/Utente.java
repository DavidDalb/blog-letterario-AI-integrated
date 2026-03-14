package it.david.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "utenti")
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, unique = true)
	private String username;
	@Column(nullable = true, unique = true)
	private String email;

	@OneToMany(mappedBy = "autore", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Recensione> recensioni = new ArrayList<>();

	public Utente() {
	}

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

	public List<Recensione> getRecensioni() {
		return recensioni;
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
