package it.david.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RecensioneDTO {

	//DTO ha la struttura di una classe POJO Soltanto non contiene attributi sensibili
	//non validata perchè viene automaticamente creata
    private Long id;
    
    
    @Min(value = 1, message = "Il voto minimo è 1")
    @Max(value = 5, message = "Il voto massimo è 5")
    private int valutazioneStelle;                      //@NotNull non è applicabile ai primitivi @NotBlank funziona solo sulle stringhe
    
    @NotBlank(message = "La recensione non può essere vuota")
    @Size(min = 1 , max = 300, message = "La recensione è compresa tra 1 e 300 caratteri")
    private String contenuto;
    
    //non validata perchè viene automaticamente creata
    private LocalDateTime dataCreazione;
    @NotNull(message = "L'id del libro non può essere vuoto")
    private Long libroId;
    @NotNull(message = "L'id utente non può essere vuoto")
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
