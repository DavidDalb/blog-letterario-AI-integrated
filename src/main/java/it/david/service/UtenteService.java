package it.david.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page; // Import per la paginazione
import org.springframework.data.domain.Pageable; // Import per la paginazione
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.david.dto.UtenteDTO;
import it.david.dto.auth.JwtResponse;
import it.david.dto.auth.LoginRequest;
import it.david.dto.auth.RegisterRequest;
import it.david.mapper.UtenteMapper;
import it.david.model.Eruolo;
import it.david.model.Ruolo;
import it.david.model.Utente;
import it.david.repository.RuoloRepository;
import it.david.repository.UtenteRepository;
import it.david.security.jwt.JwtUtils;

@Service
@Transactional(readOnly = true)
public class UtenteService {

	private static final Logger log = LoggerFactory.getLogger(UtenteService.class);

	private final UtenteRepository utenteRepository;
	private final UtenteMapper utenteMapper;
	private final RuoloRepository ruoloRepository;
	
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;

	
	public UtenteService(UtenteRepository utenteRepository, UtenteMapper utenteMapper, RuoloRepository ruoloRepository,
			PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
		
		this.utenteRepository = utenteRepository;
		this.utenteMapper = utenteMapper;
		this.ruoloRepository = ruoloRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	

	@Transactional
	public UtenteDTO register(RegisterRequest registrationDto) {
		log.info("Tentativo di registrazione dell'utente {}", registrationDto.getUsername());
	
		if (utenteRepository.existsByEmail(registrationDto.getEmail())) {
			log.warn("l'email {} esiste gia", registrationDto.getEmail());
			throw new IllegalArgumentException("Un utente con questa email esiste gia");
		}
		Utente utenteNuovo = utenteMapper.toEntity(registrationDto);
		utenteNuovo.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
		
		Set<Ruolo> ruoliDaSalvareDB = new HashSet<>();
		Set<String> ruoliUtenteRequest = registrationDto.getRuoli();
		
		if(ruoliUtenteRequest == null || ruoliUtenteRequest.isEmpty()) {
			//Se il ruolo non esiste assegna ROLE_UTENTE DI DEFAULT
			Ruolo ruoloUtente = ruoloRepository.findByRuolo(Eruolo.ROLE_UTENTE)
			.orElseThrow(() -> new RuntimeException("Ruolo non trovato"));
			ruoliDaSalvareDB.add(ruoloUtente);
		}else {
			for(String ruolo : ruoliUtenteRequest) {
				Eruolo enumCercata = Eruolo.valueOf("ROLE_" + ruolo.toUpperCase());
				
				Ruolo ruoloTrovato = ruoloRepository.findByRuolo(enumCercata)
			    .orElseThrow(() -> new RuntimeException("Ruolo nel database non trovato"));
				
				ruoliDaSalvareDB.add(ruoloTrovato);
			}
		}
		utenteNuovo.setRuoli(ruoliDaSalvareDB);
		Utente utenteSalvato = utenteRepository.save(utenteNuovo);
		
		return utenteMapper.toDto(utenteSalvato);
		
	}
	@Transactional
	public JwtResponse login(LoginRequest loginRequest) {
		UsernamePasswordAuthenticationToken authInput = new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(),
				loginRequest.getPassword());
		
		Authentication authentication = authenticationManager.authenticate(authInput);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList();
		
		return new JwtResponse(jwt, userDetails.getUsername(), roles);
	}
	
	public Page<UtenteDTO> findAllUtenti(Pageable pageable) {
		log.info("Richiesta di tutti gli utenti - Pagina {} contenente {} elementi", pageable.getPageNumber(), pageable.getPageSize());
		Page<Utente> utenti = utenteRepository.findAll(pageable);
		if (utenti.isEmpty()) {
			log.warn("Nessun utente trovato nel database");
			throw new RuntimeException("Nessun utente trovato");
		}
		log.info("Restituiti {} utenti", utenti.getNumberOfElements());
		return utenti.map(utente -> utenteMapper.toDto(utente));
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

	public Page<UtenteDTO> findByUsernameContainingIgnoreCase(String username, Pageable pageable) {
		log.info("Ricerca utenti per username contenente: {} - Pagina {} contenente {} elementi", username, pageable.getPageNumber(), pageable.getPageSize());
		if (username == null || username.isEmpty()) {
			log.warn("Tentativo di ricerca utenti con username vuoto o nullo");
			throw new IllegalArgumentException("Username non può essere vuoto");
		}
		Page<Utente> utenti = utenteRepository.findByUsernameContainingIgnoreCase(username, pageable);
		if (utenti.isEmpty()) {
			log.warn("Nessun utente trovato con username: {}", username);
			throw new RuntimeException("Nessun utente trovato con username: " + username);
		}
		log.info("Trovati {} utenti per username: {}", utenti.getNumberOfElements(), username);
		return utenti.map(utente -> utenteMapper.toDto(utente));
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
