package it.david.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.david.dto.UtenteDTO;
import it.david.mapper.UtenteMapper;
import it.david.model.Utente;
import it.david.repository.UtenteRepository;

@Service
@Transactional(readOnly = true)
public class UtenteService {

	private final UtenteRepository utenteRepository;
	private final UtenteMapper utenteMapper;

	public UtenteService(UtenteRepository utenteRepository, UtenteMapper utenteMapper) {
		this.utenteRepository = utenteRepository;
		this.utenteMapper = utenteMapper;
	}

	public List<UtenteDTO> findAllUtenti() {
		List<Utente> utenti = utenteRepository.findAll();
		if (utenti.isEmpty()) {
			throw new RuntimeException("Nessun utente trovato");
		}
		return utenteMapper.toDtoList(utenti);
	}

	public UtenteDTO findUtenteById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Id non può essere vuoto");
		}
		Utente utente = utenteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Utente non trovato con ID: " + id));
		return utenteMapper.toDto(utente);
	}

	public List<UtenteDTO> findByUsernameContainingIgnoreCase(String username) {
		if (username == null || username.isEmpty()) {
			throw new IllegalArgumentException("Username non può essere vuoto");
		}
		List<Utente> utenti = utenteRepository.findByUsernameContainingIgnoreCase(username);
		if (utenti.isEmpty()) {
			throw new RuntimeException("Nessun utente trovato con username: " + username);
		}
		return utenteMapper.toDtoList(utenti);
	}

	@Transactional
	public UtenteDTO saveUtente(UtenteDTO utenteDto) {
		if (utenteDto == null) {
			throw new IllegalArgumentException("L'utente non può essere vuoto");
		}
		if (utenteRepository.existsByEmail(utenteDto.getEmail())) {
			throw new IllegalArgumentException("Un utente con questa email esiste già");
		}
		Utente utenteDaSalvare = utenteMapper.toEntity(utenteDto);
		Utente utenteSalvato = utenteRepository.save(utenteDaSalvare);
		
		return utenteMapper.toDto(utenteSalvato);
	}

	@Transactional
	public UtenteDTO updateUtente(Long id, UtenteDTO utenteDto) {
		if (utenteDto == null || id == null) {
			throw new IllegalArgumentException("Dati ID/Utente mancanti per l'aggiornamento");
		}
		Utente utenteEsistente = utenteRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Utente non trovato con ID: " + id));

		utenteEsistente.setUsername(utenteDto.getUsername());
		
		//existByEmailAndIdNot Verifica se l'email è già usata da altri utenti non considerando L'id dello stesso
		if (utenteRepository.existsByEmailAndIdNot(utenteDto.getEmail(), id)) {
			throw new IllegalArgumentException("Email gia occupata da un'altro Utente");
		}
	    utenteEsistente.setEmail(utenteDto.getEmail());
		Utente utenteAggiornato = utenteRepository.save(utenteEsistente);
		return utenteMapper.toDto(utenteAggiornato);
	}


	@Transactional
	public void deleteUtenteById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Id non può essere vuoto");
		}
		if (!utenteRepository.existsById(id)) {
            throw new IllegalArgumentException("Utente non trovato con ID: " + id);
        }
		utenteRepository.deleteById(id);
	}
}
