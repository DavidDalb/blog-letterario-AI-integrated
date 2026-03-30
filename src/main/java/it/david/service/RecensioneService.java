package it.david.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.david.dto.RecensioneDTO;
import it.david.mapper.RecensioneMapper;
import it.david.model.Libro;
import it.david.model.Recensione;
import it.david.model.Utente;
import it.david.repository.LibroRepository;
import it.david.repository.RecensioneRepository;
import it.david.repository.UtenteRepository;

@Service
@Transactional(readOnly = true) // Dice a Spring di default che i metodi leggono solo e non devono bloccare le
								// tabelle, senza Transactional le tabelle
								// vengono fraintese attendendo modifiche che non arriveranno e consumerà
								// risorse e metterà utenti in coda.
public class RecensioneService {
	private static final Logger log = LoggerFactory.getLogger(RecensioneService.class);

	private final RecensioneRepository recensioneRepository;
	private final LibroRepository libroRepository; // Iniettato (per "pescare" il padre nel metodo save)
	private final RecensioneMapper recensioneMapper;
	private final UtenteRepository utenteRepository;

	public RecensioneService(RecensioneRepository recensioneRepository, LibroRepository libroRepository,
			RecensioneMapper recensioneMapper, UtenteRepository utenteRepository) {
		this.recensioneRepository = recensioneRepository;
		this.libroRepository = libroRepository;
		this.recensioneMapper = recensioneMapper;
		this.utenteRepository = utenteRepository;
	}

	// RuntimeException è usata per errori di esecuzione
	public Page<RecensioneDTO> findAll(Pageable pageable) {
		log.info("Richiesta di tutte le recensioni con paginazione - Pagina: {}, Dimensione: {}, Ordine: {}",
				pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
		Page<Recensione> recensioniPage = recensioneRepository.findAll(pageable);
		if (!recensioniPage.hasContent()) {
			log.warn("Nessuna recensione trovata nel database per la pagina corrente");
			// Puoi scegliere di lanciare un'eccezione o restituire una pagina vuota.
			// Restituisco una pagina vuota per un comportamento più standard con la paginazione.
		}
		log.info("Restituite {} recensioni su {} totali per la pagina corrente", recensioniPage.getNumberOfElements(), recensioniPage.getTotalElements());
		return recensioniPage.map(recensione -> recensioneMapper.toDto(recensione));
	}

	// IllegalArgumentException è usata per errori di validazione dei dati
	public RecensioneDTO findById(Long id) {
		log.info("Ricerca recensione con ID: {}", id);
		if (id == null) {
			log.warn("Tentativo di ricerca recensione con ID nullo");
			throw new IllegalArgumentException("Id non può essere vuoto");
		}
		Optional<Recensione> recensioneOpt = recensioneRepository.findById(id);
		if (recensioneOpt.isEmpty()) {
			log.warn("Recensione con ID {} non trovata", id);
			throw new IllegalArgumentException("Recensione non trovata con ID: " + id);
		}
		Recensione recensione = recensioneOpt.get();
		log.info("Recensione trovata con ID: {}", id);
		return recensioneMapper.toDto(recensione);
	}

	public Page<RecensioneDTO> findByValutazioneStelleGreaterThanEqual(int valutazioneStelle, Pageable pageable) {
		log.info("Ricerca recensioni con valutazione stelle maggiore o uguale a {} con paginazione - Pagina: {}, Dimensione: {}, Ordine: {}",
				valutazioneStelle, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
		if (valutazioneStelle <= 0 || valutazioneStelle > 5) {
			log.warn("Tentativo di ricerca con valutazione stelle non valida: {}", valutazioneStelle);
			throw new IllegalArgumentException("Il voto non può essere minore di 0 o maggiore di 5");
		}
		Page<Recensione> recensioniPage = recensioneRepository.findByValutazioneStelleGreaterThanEqual(valutazioneStelle, pageable);
		log.info("Trovate {} recensioni su {} totali con valutazione stelle maggiore o uguale a {} per la pagina corrente",
				recensioniPage.getNumberOfElements(), recensioniPage.getTotalElements(), valutazioneStelle);
		return recensioniPage.map(recensione -> recensioneMapper.toDto(recensione));
	}

	// (Di spring) se durante il metodo succede un errore fa rollback
	@Transactional
	public RecensioneDTO saveRecensione(RecensioneDTO recensioneDto) {
		log.info("Tentativo di salvare una nuova recensione.");
		if (recensioneDto == null) {
			log.warn("Tentativo di salvare una recensione null");
			throw new IllegalArgumentException("La recensione non può essere vuota");
		}
		log.debug("Dettagli recensione da salvare: valutazioneStelle='{}', libroId='{}', utenteId='{}'",
				recensioneDto.getValutazioneStelle(), recensioneDto.getLibroId(), recensioneDto.getUtenteId());

		// Recensione è figlia di Libro - (Cerco prima il padre con id)
		Libro libro = libroRepository.findById(recensioneDto.getLibroId())
				.orElseThrow(() -> {                                               //orElseThrow Può avere un body, e quando c'è il return diventa obbligatorio
					log.warn("Libro non trovato con ID: {}", recensioneDto.getLibroId());
					return new IllegalArgumentException("Libro non trovato");
				});
		Utente utente = utenteRepository.findById(recensioneDto.getUtenteId())
	            .orElseThrow(() -> {
	            	log.warn("Utente non trovato con ID: {}", recensioneDto.getUtenteId());
	            	return new IllegalArgumentException("Utente non trovato");
	            });
		// Da DTO ad Entity perchè non ha ancora un Id e il repo accetta solo entity
				// 'Recensione'
		Recensione recensioneDaSalvare = recensioneMapper.toEntity(recensioneDto);
		// Per collegare la recensione al libro, altrimenti la tabella libro_id in
		// recensioni.model sarà null
		recensioneDaSalvare.setLibro(libro);
		recensioneDaSalvare.setAutore(utente);
		// Salvando in questo modo ottiene un Id
		Recensione RecensioneSalvato = recensioneRepository.save(recensioneDaSalvare);
		log.info("Recensione salvata con successo con ID: {} per libro ID: {} e utente ID: {}",
				RecensioneSalvato.getId(), RecensioneSalvato.getLibro().getId(), RecensioneSalvato.getAutore().getId());

		return recensioneMapper.toDto(RecensioneSalvato);
	}

	// DTO non necessario perchè il metodo non deve restituire niente
	public void deleteRecensioneById(Long id) {
		log.info("Tentativo di eliminare la recensione con ID: {}", id);
		if (id == null) {
			log.warn("Tentativo di eliminare recensione con ID nullo");
			throw new IllegalArgumentException("Id non può essere vuoto");
		}
		if (!recensioneRepository.existsById(id)) {
            log.warn("Tentativo di eliminare recensione non esistente con ID: {}", id);
            throw new IllegalArgumentException("Recensione non trovata con ID: " + id);
        }
		recensioneRepository.deleteById(id);
		log.info("Recensione con ID: {} eliminata con successo", id);
	}

	@Transactional
	public RecensioneDTO updateRecensione(Long id, RecensioneDTO recensioneDto) {
		log.info("Tentativo di aggiornare la recensione con ID: {}", id);
		if (recensioneDto == null || id == null) {
			log.warn("Dati ID/Recensione mancanti per l'aggiornamento - ID: {}", id);
			throw new IllegalArgumentException("Dati mancanti per l'aggiornamento");
		}
		Recensione recensione = recensioneRepository.findById(id)
				.orElseThrow(() -> {
					log.warn("Recensione non trovata per l'aggiornamento con ID: {}", id);
					return new IllegalArgumentException("Recensione non trovata con ID: " + id);
				});
		log.debug("Aggiornamento recensione ID: {}. Dati prima: valutazioneStelle={}, contenuto='{}'",
				id, recensione.getValutazioneStelle(), recensione.getContenuto());

		Libro libro = libroRepository.findById(recensioneDto.getLibroId())
				.orElseThrow(() -> {
					log.warn("Libro non trovato per l'aggiornamento della recensione ID {} con LibroId: {}", id, recensioneDto.getLibroId());
					return new IllegalArgumentException("Libro non trovato");
				});
		
		Utente utente = utenteRepository.findById(recensioneDto.getUtenteId())        
				.orElseThrow(() -> {
					log.warn("Utente non trovato per l'aggiornamento della recensione ID {} con UtenteId: {}", id, recensioneDto.getUtenteId());
					return new IllegalArgumentException("Utente non trovato");
				});
		
		recensione.setContenuto(recensioneDto.getContenuto());
		recensione.setValutazioneStelle(recensioneDto.getValutazioneStelle());
		
		//relazioni
		recensione.setLibro(libro);
	    recensione.setAutore(utente);                                                        
		Recensione recensioneSalvata = recensioneRepository.save(recensione);
		
		log.info("Recensione con ID: {} aggiornata con successo. Dati dopo: valutazioneStelle={}, contenuto='{}'",
				id, recensioneSalvata.getValutazioneStelle(), recensioneSalvata.getContenuto());
		return recensioneMapper.toDto(recensioneSalvata);
		
	}  
		
}
