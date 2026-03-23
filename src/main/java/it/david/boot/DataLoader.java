package it.david.boot;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import it.david.model.Libro;
import it.david.model.Recensione;
import it.david.model.Utente;
import it.david.repository.LibroRepository;
import it.david.repository.RecensioneRepository;
import it.david.repository.UtenteRepository;

@Component
public class DataLoader implements CommandLineRunner {

	// Final per garantire che i repository siano sempre presenti e mai null
	private final LibroRepository libroRepository;
	private final UtenteRepository utenteRepository;
	private final RecensioneRepository recensioneRepository;

	public DataLoader(LibroRepository libroRepository, UtenteRepository utenteRepository,
			RecensioneRepository recensioneRepository) {
		this.libroRepository = libroRepository;
		this.utenteRepository = utenteRepository;
		this.recensioneRepository = recensioneRepository;
	}

	@Override
	public void run(String... args) throws Exception {

		if (libroRepository.count() > 0) {
			System.out.println("I dati del DataLoader sono gia presenti");
			return;
		}

		Utente u1 = new Utente();
		u1.setUsername("Pasquale");
		u1.setEmail("Pasquale@test.com");
		u1.setPassword("password");
		utenteRepository.save(u1);

		Libro l1 = new Libro();
		l1.setTitolo("Il giovane Holden");
		l1.setAutore("J. D. Salinger");
		l1.setGenere("Romanzo di formazione");
		libroRepository.save(l1);

		Recensione r1 = new Recensione();
		r1.setContenuto(
				"Mi è piaciuto tanto il fatto che è narrato in prima persona a modo diario ed inizia senza spiegare nulla di Holden."
						+ " Infatti il lettore poi leggendo capisce autonomamente tante cose, senza essergli spiegato quasi nulla."
						+ " Affronta temi vari legati principalmente all’adolescenza e al passaggio alla fase adulta, ma anche temi più distaccati."
						+ " Consiglio molto, è una bella lettura.");
		r1.setValutazioneStelle(4);
		r1.setLibro(l1);
		r1.setAutore(u1);
		r1.setDataCreazione(LocalDateTime.now());
		recensioneRepository.save(r1);
		
		System.out.println("INFO: Database popolato con successo");
	}
	

}
