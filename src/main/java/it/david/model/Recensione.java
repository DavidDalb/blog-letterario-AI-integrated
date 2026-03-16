package it.david.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recensioni")
public class Recensione {

	// Attributi
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "libro_id")
	private Libro libro;

	@ManyToOne
	@JoinColumn(name = "utente_id")
	private Utente autore;

	@Column(nullable = false)
	private int valutazioneStelle;

	@Column(length = 2000, nullable = false)
	private String contenuto;

	@Column(name = "data_creazione", nullable = false)
	private LocalDateTime dataCreazione;

	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Utente getAutore() {
		return autore;
	}

	public void setAutore(Utente autore) {
		this.autore = autore;
	}

	// Costruttori
	public Recensione() {
	}

	public Recensione(int valutazioneStelle, String contenuto, LocalDateTime dataCreazione) {

		this.valutazioneStelle = valutazioneStelle;
		this.contenuto = contenuto;
		this.dataCreazione = dataCreazione;
	}
	// Getter & Setter

	public int getValutazioneStelle() {
		return valutazioneStelle;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public void setValutazioneStelle(int valutazioneStelle) {
		this.valutazioneStelle = valutazioneStelle;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	@Override
	public String toString() {
		return " id recensione: " + id + " valutazioneStelle: " + valutazioneStelle + " contenuto: " + contenuto
				+ " dataCreazione: " + dataCreazione;
	}

}
