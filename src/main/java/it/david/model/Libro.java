package it.david.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "libri")
public class Libro {

	// Attributi
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titolo;
	private String autore;
	private String genere;

	@OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Recensione> recensioni = new ArrayList<>();

	// Costruttori
	public Libro() {

	}

	public Libro(String titolo, String autore, String genere) {
		super();
		this.titolo = titolo;
		this.autore = autore;
		this.genere = genere;
	}

	// Getter & Setter
	public List<Recensione> getRecensioni() {
		return recensioni;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public Long getId() {
		return id;
	}

	// Necessario per MapStruct: permette al Bean Mapper di popolare i campi
	public void setId(Long id) {
		this.id = id;
	}

	// Necessario per MapStruct: permette al Bean Mapper di popolare i campi
	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}

	@Override
	public String toString() {
		return " id libro: " + id + " titolo: " + titolo + " autore: " + autore + " genere: " + genere;
	}

}
