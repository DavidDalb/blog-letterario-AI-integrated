package it.david.model;

import java.time.LocalDateTime;

public class Recensione {
	
	//Attributi
	private Long id;
	
	private Libro libro;
	
	private int valutazioneStelle;
    private String contenuto;
    private LocalDateTime dataCreazione;
	
	//Costruttori
	public Recensione(){}
	
	public Recensione(int valutazioneStelle, String contenuto, LocalDateTime dataCreazione){
		
		
		this.valutazioneStelle = valutazioneStelle;
		this.contenuto = contenuto;
		this.dataCreazione = dataCreazione;
	}
	//Getter & Setter


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
		return " id: " + id + " valutazioneStelle: " + valutazioneStelle + " contenuto: " + contenuto
				+ " dataCreazione: " + dataCreazione;
	}

	
	
	
}
