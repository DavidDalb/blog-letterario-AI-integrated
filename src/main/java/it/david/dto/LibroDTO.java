package it.david.dto;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LibroDTO {

	//L'id non si valida, perchè lo genera il DB 
    private Long id;                                            //Questi Validator partono quando il flusso incontra il controller
    															//@NotNull Non va bene per le String perchè "" e " " li considera validi
	@NotBlank(message = "Il titolo non può essere vuoto")    
	@Size(min = 1, max = 40, message = "Il titolo deve essere tra 1 e 40 caratteri")
    private String titolo;
	
	@NotBlank(message = "L'autore non può essere vuoto")
	@Size(min = 1, max = 30, message = "L'autore deve essere tra 1 e 30 caratteri")
    private String autore;
	
	@NotBlank(message = "Il genere non può essere vuoto")
	@Size(min = 1, max = 30, message = "Il genere deve essere tra 1 e 30 caratteri")
    private String genere;

    
    public LibroDTO() {
    }

    
    public LibroDTO(Long id, String titolo, String autore, String genere) {
        this.id = id;
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibroDTO libroDTO = (LibroDTO) o;
        return Objects.equals(id, libroDTO.id) &&
               Objects.equals(titolo, libroDTO.titolo) &&
               Objects.equals(autore, libroDTO.autore) &&
               Objects.equals(genere, libroDTO.genere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titolo, autore, genere);
    }

    
    @Override
    public String toString() {
        return "LibroDTO{" +
               "id=" + id +
               ", titolo='" + titolo + '\'' +
               ", autore='" + autore + '\'' +
               ", genere='" + genere + '\'' +
               '}';
    }
}
