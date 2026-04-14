package it.david.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "utenti")
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    //Nullable = false è notNull, invece true può essere NULL
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;

	@OneToMany(mappedBy = "autore", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Recensione> recensioni = new ArrayList<>();
	
	// LAZY = Carica i ruoli dal DB solo chiamando .getRoles() (per ottimizzare)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "utente_ruoli",
	joinColumns = @JoinColumn(name = "utente_id"),
	inverseJoinColumns = @JoinColumn(name = "ruolo_id"))
	private Set<Ruolo> ruoli = new HashSet<>();

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
	// Necessario per MapStruct: permette al Bean Mapper di popolare i campi
	public void setId(Long id) {
		this.id = id;
	}
	// Necessario per MapStruct: permette al Bean Mapper di popolare i campi
	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
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
	
	

	public Set<Ruolo> getRuoli() {
		return ruoli;
	}

	public void setRuoli(Set<Ruolo> ruoli) {
		this.ruoli = ruoli;
	}

	public void addRuolo(Ruolo ruolo) {
		this.ruoli.add(ruolo);
	}
	@Override
	public String toString() {
		return " id utente: " + id + " username: " + username + " email: " + email;
	}

}
