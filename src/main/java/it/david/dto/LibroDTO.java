package it.david.dto;

import java.util.Objects;

public class LibroDTO {

    private Long id;
    private String titolo;
    private String autore;
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
