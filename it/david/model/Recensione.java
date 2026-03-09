package it.david.model;

import java.time.LocalDateTime;

public class Recensione {
	
	//Attributi
	private Long id;
	
	private String titoloLibro;
	private int valutazioneStelle;
	private String contenuto;
	private LocalDateTime dataCreazione;
	
	//Costruttori
	public Recensione(){}
	
	public Recensione(String titoloLibro, int valutazioneStelle, String contenuto, LocalDateTime dataCreazione){
		
		this.titoloLibro = titoloLibro;
		this.valutazioneStelle = valutazioneStelle;
		this.contenuto = contenuto;
		this.dataCreazione = dataCreazione;
	}
	//Getter & Setter

	public String getTitoloLibro() {
		return titoloLibro;
	}

	public void setTitoloLibro(String titoloLibro) {
		this.titoloLibro = titoloLibro;
	}

	public int getValutazioneStelle() {
		return valutazioneStelle;
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
		return " id: " + id + " titoloLibro: " + titoloLibro + " valutazioneStelle: " + valutazioneStelle
				+ " contenuto: " + contenuto + " dataCreazione: " + dataCreazione;
	}
	
	
}
