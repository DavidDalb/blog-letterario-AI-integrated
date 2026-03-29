package it.david.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.david.dto.UtenteDTO;
import it.david.mapper.UtenteMapper;
import it.david.model.Utente;
import it.david.repository.UtenteRepository;

@Service
@Transactional(readOnly = true)
public class UtenteService {

	private static final Logger log = LoggerFactory.getLogger(UtenteService.class);

	private final UtenteRepository utenteRepository;
	private final UtenteMapper utenteMapper;

	public UtenteService(UtenteRepository utenteRepository, UtenteMapper utenteMapper) {
		this.utenteRepository = utenteRepository;
		this.utenteMapper = utenteMapper;
	}

	public List<UtenteDTO> findAllUtenti() {
		log.info("Richiesta di tutti gli utenti");
		List<Utente> utenti = utenteRepository.findAll();
		if (utenti.isEmpty()) {
			log.warn("Nessun utente trovato nel database");
			throw new RuntimeException("Nessun utente trovato");
		}
		log.info("Restituiti {} utenti", utenti.size());
		return utenteMapper.toDtoList(utenti);
	}

	public UtenteDTO findUtenteById(Long id) {
		log.info("Ricerca utente con ID: {}", id);
		if (id == null) {
			log.warn("Tentativo di ricerca utente con ID nullo");
			throw new IllegalArgumentException("Id non può essere vuoto");
		}
		Utente utente = utenteRepository.findById(id)
				.orElseThrow(() -> {
					log.warn("Utente non trovato con ID: {}", id);
					return new IllegalArgumentException("Utente non trovato con ID: " + id);
				});
		log.info("Utente trovato con ID: {}", id);
		return utenteMapper.toDto(utente);
	}

	public List<UtenteDTO> findByUsernameContainingIgnoreCase(String username) {
		log.info("Ricerca utenti per username contenente: {}", username);
		if (username == null || username.isEmpty()) {
			log.warn("Tentativo di ricerca utenti con username vuoto o nullo");
			throw new IllegalArgumentException("Username non può essere vuoto");
		}
		List<Utente> utenti = utenteRepository.findByUsernameContainingIgnoreCase(username);
		if (utenti.isEmpty()) {
			log.warn("Nessun utente trovato con username: {}", username);
			throw new RuntimeException("Nessun utente trovato con username: " + username);
		}
		log.info("Trovati {} utenti per username: {}", utenti.size(), username);
		return utenteMapper.toDtoList(utenti);
	}

	@Transactional
	public UtenteDTO saveUtente(UtenteDTO utenteDto) {
		log.info("Tentativo di salvare un nuovo utente."); 
		if (utenteDto == null) {
			log.warn("Tentativo di salvare un utente null");
			throw new IllegalArgumentException("L'utente non può essere vuoto");
		}
		log.info("Dettagli utente da salvare: username='{}', email='{}'", utenteDto.getUsername(), utenteDto.getEmail()); 
		if (utenteRepository.existsByEmail(utenteDto.getEmail())) {
			log.warn("Tentativo di salvare utente con email già esistente: {}", utenteDto.getEmail());
			throw new IllegalArgumentException("Un utente con questa email esiste già");
		}
		Utente utenteDaSalvare = utenteMapper.toEntity(utenteDto);
		Utente utenteSalvato = utenteRepository.save(utenteDaSalvare);
		log.info("Utente '{}' salvato con successo con ID: {}", utenteSalvato.getUsername(), utenteSalvato.getId());
		
		return utenteMapper.toDto(utenteSalvato);
	}

	@Transactional
	public UtenteDTO updateUtente(Long id, UtenteDTO utenteDto) {
		log.info("Tentativo di aggiornare l'utente con ID: {}", id);
		if (utenteDto == null || id == null) {
			log.warn("Dati ID/Utente mancanti per l'aggiornamento - ID: {}", id);
			throw new IllegalArgumentException("Dati ID/Utente mancanti per l'aggiornamento");
		}
		Utente utenteEsistente = utenteRepository.findById(id)
				.orElseThrow(() -> {
					log.warn("Utente non trovato per l'aggiornamento con ID: {}", id);
					return new IllegalArgumentException("Utente non trovato con ID: " + id);
				});
        //Per capire il flusso di dati
		log.debug("Aggiornamento username da '{}' a '{}' per utente ID: {}", utenteEsistente.getUsername(), utenteDto.getUsername(), id);
		utenteEsistente.setUsername(utenteDto.getUsername());
		
		//existByEmailAndIdNot Verifica se l'email è già usata da altri utenti non considerando L'id dello stesso
		if (utenteRepository.existsByEmailAndIdNot(utenteDto.getEmail(), id)) {
			log.warn("L'email '{}' è già occupata da un altro utente", utenteDto.getEmail());
			throw new IllegalArgumentException("Email gia occupata da un'altro Utente");
		}
	    log.debug("Aggiornamento email da '{}' a '{}' per utente ID: {}", utenteEsistente.getEmail(), utenteDto.getEmail(), id);
	    utenteEsistente.setEmail(utenteDto.getEmail());
		Utente utenteAggiornato = utenteRepository.save(utenteEsistente);
		log.info("Utente con ID: {} aggiornato con successo", id);
		return utenteMapper.toDto(utenteAggiornato);
	}


	@Transactional
	public void deleteUtenteById(Long id) {
		log.info("Tentativo di eliminare l'utente con ID: {}", id);
		if (id == null) {
			log.warn("Tentativo di eliminare utente con ID nullo");
			throw new IllegalArgumentException("Id non può essere vuoto");
		}
		if (!utenteRepository.existsById(id)) {
			log.warn("Tentativo di eliminare utente non esistente con ID: {}", id);
            throw new IllegalArgumentException("Utente non trovato con ID: " + id);
        }
		utenteRepository.deleteById(id);
		log.info("Utente con ID: {} eliminato con successo", id);
	}
}
