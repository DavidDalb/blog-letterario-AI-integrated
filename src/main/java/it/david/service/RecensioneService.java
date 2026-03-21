package it.david.service;

import java.util.List;
import java.util.Optional;

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
	public List<RecensioneDTO> findAll() {
		List<Recensione> recensioni = recensioneRepository.findAll();
		if (recensioni.isEmpty()) {
			throw new RuntimeException("Nessuna recensione trovata");
		}
		return recensioneMapper.toDtoList(recensioni);
	}

	// IllegalArgumentException è usata per errori di validazione dei dati
	public RecensioneDTO findById(Long id) {
		Optional<Recensione> recensioneOpt = recensioneRepository.findById(id);

		if (recensioneOpt.isEmpty()) {
			throw new IllegalArgumentException("Id non può essere vuoto");
		}
		Recensione recensione = recensioneOpt.get();
		return recensioneMapper.toDto(recensione);
	}

	public List<RecensioneDTO> findByValutazioneStelleGreaterThanEqual(int valutazioneStelle) {
		if (valutazioneStelle <= 0 || valutazioneStelle > 5) {
			throw new IllegalArgumentException("Il voto non può essere minore di 0 o maggiore di 5");
		}
		List<Recensione> risultato = recensioneRepository.findByValutazioneStelleGreaterThanEqual(valutazioneStelle);
		return recensioneMapper.toDtoList(risultato);
	}

	// (Di spring) se durante il metodo succede un errore fa rollback
	@Transactional
	public RecensioneDTO saveRecensione(RecensioneDTO recensioneDto) {

		// Recensione è figlia di Libro - (Cerco prima il padre con id)
		Libro libro = libroRepository.findById(recensioneDto.getLibroId())
				.orElseThrow(() -> new IllegalArgumentException("Libro non trovato"));

		// Da DTO ad Entity perchè non ha ancora un Id e il repo accetta solo entity
		// 'Recensione'
		Recensione recensioneDaSalvare = recensioneMapper.toEntity(recensioneDto);
		// Per collegare la recensione al libro, altrimenti la tabella libro_id in
		// recensioni.model sarà null
		recensioneDaSalvare.setLibro(libro);
		// Salvando in questo modo ottiene un Id
		Recensione RecensioneSalvato = recensioneRepository.save(recensioneDaSalvare);

		return recensioneMapper.toDto(RecensioneSalvato);
	}

	// DTO non necessario perchè il metodo non deve restituire niente
	public void deleteRecensioneById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Id non può essere vuoto");
		}
		recensioneRepository.deleteById(id);
	}

	@Transactional
	public RecensioneDTO updateRecensione(Long id, RecensioneDTO recensioneDto) {
		if (recensioneDto == null || id == null) {
			throw new IllegalArgumentException("Dati mancanti per l'aggiornamento");
		}
		Recensione recensione = recensioneRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Recensione non trovata"));
		
		Libro libro = libroRepository.findById(recensioneDto.getLibroId())
				.orElseThrow(() -> new IllegalArgumentException("Libro non trovato"));
		
		Utente utente = utenteRepository.findById(recensioneDto.getUtenteId())        
				.orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));                                   
		recensione.setContenuto(recensioneDto.getContenuto());
		recensione.setValutazioneStelle(recensioneDto.getValutazioneStelle());
		
		//relazioni
		recensione.setLibro(libro);
	    recensione.setAutore(utente);                                                        
		Recensione recensioneSalvata = recensioneRepository.save(recensione);
		
		return recensioneMapper.toDto(recensioneSalvata);
		
	}  
		
}
