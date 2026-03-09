package it.david.model;

public class Libro {
	
	//Attributi
	private Long id;
	
	private String titolo,autore,genere;
	
	//Costruttori
	public Libro() {
		
	}

	public Libro(String titolo, String autore, String genere) {
		super();
		this.titolo = titolo;
		this.autore = autore;
		this.genere = genere;
	}

	//Getter & Setter
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

	@Override
	public String toString() {
		return " id libro: " + id + " titolo: " + titolo + " autore: " + autore + " genere: " + genere;
	}
	
	
}
