package it.david.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class RecensioneDTO {

	//DTO ha la struttura di una classe POJO Soltanto non contiene attributi sensibili
    private Long id;
    private int valutazioneStelle;
    private String contenuto;
    private LocalDateTime dataCreazione;
    private Long libroId;
    private Long utenteId;

    
    public RecensioneDTO() {
    }


	public Long getId() {
		return id;
	}


	public Long getUtenteId() {
		return utenteId;
	}


	public void setUtenteId(Long utenteId) {
		this.utenteId = utenteId;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getLibroId() {
		return libroId;
	}


	public void setLibroId(Long libroId) {
		this.libroId = libroId;
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


	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}


	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	@Override
	public int hashCode() {
		return Objects.hash(contenuto, dataCreazione, id, valutazioneStelle);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RecensioneDTO))
			return false;
		RecensioneDTO other = (RecensioneDTO) obj;
		return Objects.equals(contenuto, other.contenuto) && Objects.equals(dataCreazione, other.dataCreazione)
				&& Objects.equals(id, other.id) && valutazioneStelle == other.valutazioneStelle;
	}


	@Override
	public String toString() {
		return " id: " + id + " valutazioneStelle: " + valutazioneStelle + " contenuto: " + contenuto
				+ " dataCreazione: " + dataCreazione;
	}


}
