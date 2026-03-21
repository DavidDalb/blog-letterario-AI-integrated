package it.david.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.david.model.Libro;
import it.david.model.Utente;
import it.david.repository.UtenteRepository;

@Service
public class UtenteService {

	private final UtenteRepository utenteRepository;

	public UtenteService(UtenteRepository utenteRepository) {
		this.utenteRepository = utenteRepository;
	}

	public List<Utente> findAllUtenti() {
		List<Utente> utenti = utenteRepository.findAll();
		if (utenti.isEmpty()) {
			throw new RuntimeException("Nessun utente trovato");
		}
		return utenti;
	}

	public Optional<Utente> findUtenteById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Id non può essere vuoto");
		}
		return utenteRepository.findById(id);
	}

	public Utente saveUtente(Utente utente) {
		if (utente == null) {
			throw new IllegalArgumentException("utente non può essere vuoto");
		}
		boolean utenteEsistente = utenteRepository.existsByEmail(utente.getEmail());
		if (utenteEsistente) {
			throw new IllegalArgumentException("utente già esistente");
		}
		return utenteRepository.save(utente);

	}
}
