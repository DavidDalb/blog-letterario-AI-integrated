package it.david.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.david.dto.LibroDTO;
import it.david.mapper.LibroMapper;
import it.david.model.Libro;
import it.david.repository.LibroRepository;
                                                             //REMINDER*SNELLIRE IL CODICE (prendi spunto da recensioneService)
@Service
@Transactional(readOnly = true)
public class LibroService {
	
	//per configurare il messaggio di log di questa classe
	private static final Logger log = LoggerFactory.getLogger(LibroService.class); //da scrivere qui in alto

	private final LibroRepository libroRepository;
	private final LibroMapper libroMapper;

	// INJECTION by constructor
	public LibroService(LibroRepository libroRepository, LibroMapper libroMapper) {
		this.libroRepository = libroRepository;
		this.libroMapper = libroMapper;
	}

	// RuntimeException è usata per errori di esecuzione
	public Page<LibroDTO> findAll(Pageable pageable) {    //il service riceve Pageable dal controller
		log.info("Richiesta libri - Pagina {} contenente {} elementi",pageable.getPageNumber(),pageable.getPageSize());//PageSize indica quanti elementi per pagina
		Page<Libro> libri = libroRepository.findAll(pageable);
		if (libri.isEmpty()) {
			log.warn("Nessun libro nel database");
			throw new RuntimeException("Nessun libro trovato");
		}
		log.info("Restituiti {} libri", libri.getNumberOfElements());   //Placeholder è preferito alla concatenazione che invece spreca memoria
		//map itera su elementi page ogni oggetto e lo converte in Dto mantenendo metadati
		return libri.map(libro -> libroMapper.toDto(libro));      //cosi facendo evitiamo di fare return new PageImpl con i parametri a mano
	}

	// IllegalArgumentException è usata per errori di validazione dei dati
	public LibroDTO findById(Long id) {
		log.info("Ricerca libro con id {}", id);
		Optional<Libro> libroOpt = libroRepository.findById(id);

		if (libroOpt.isEmpty()) {
			log.warn("Libro con id {} non trovato", id);
			throw new IllegalArgumentException("Libro non trovato con id: " + id );
		}
		Libro libro = libroOpt.get();
		log.info("Libro trovato: {}", libro.getTitolo());
		return libroMapper.toDto(libro);
	}

	// isEmpty è per i contenitori vuoti
	public Page<LibroDTO> findByAutoreContainingIgnoreCase(String autore,Pageable pageable) {
		if (autore == null || autore.isEmpty()) {
			log.warn("Ricerca autore con campo vuoto");
			throw new IllegalArgumentException("Autore non può essere vuoto");
		}
		Page<Libro> libri = libroRepository.findByAutoreContainingIgnoreCase(autore,pageable);
		log.info("Trovati {} libri per autore: {}",libri.getNumberOfElements(), autore);
		return libri.map(libro -> libroMapper.toDto(libro));
	}

	public Page<LibroDTO> findByTitoloContainingIgnoreCase(String titolo,Pageable pageable) {
		if (titolo == null || titolo.isEmpty()) {
			log.warn("Ricerca titolo con campo vuoto");
			throw new IllegalArgumentException("Titolo non può essere vuoto");
		}
		Page<Libro> libri = libroRepository.findByTitoloContainingIgnoreCase(titolo,pageable);
		log.info("Trovati {} libri per titolo: {}", libri.getNumberOfElements(), titolo);
		return libri.map(libro -> libroMapper.toDto(libro));
	}

	public Page<LibroDTO> findByGenereContainingIgnoreCase(String genere,Pageable pageable) {
		if (genere == null || genere.isEmpty()) {
			log.warn("Ricerca genere con campo vuoto");
			throw new IllegalArgumentException("Genere non può essere vuoto");
		}
		Page<Libro> libri = libroRepository.findAllByGenereContainingIgnoreCase(genere,pageable);
		log.info("Trovati {} libri per genere: {}", libri.getNumberOfElements(), genere);
		return libri.map(libro -> libroMapper.toDto(libro));
	}

	@Transactional
	public LibroDTO saveLibro(LibroDTO libroDto) {
		if (libroDto == null) {
			log.warn("Tentativo di salvare un libro null");
			throw new IllegalArgumentException("Libro non può essere vuoto");
		}
		log.info("Tentativo di salvataggio libro: {}", libroDto.getTitolo());
		Libro libroEsistente = libroRepository.findByTitoloAndAutore(libroDto.getTitolo(), libroDto.getAutore());
		if (libroEsistente != null) {
			log.warn("Libro '{}' di {} esistente", libroDto.getTitolo(),libroDto.getAutore());
			throw new IllegalArgumentException("Libro già esistente");
		}
		//Da DTO ad Entity perchè non ha ancora un Id e il repo accetta solo entity 'Libro'
		Libro libroDaSalvare = libroMapper.toEntity(libroDto);
		//Salvando in questo modo ottiene un Id
		Libro libroSalvato = libroRepository.save(libroDaSalvare);
		log.info("Libro '{}' salvato con successo",libroDto.getTitolo());
		
		return libroMapper.toDto(libroSalvato);
		}
		

	//DTO non necessario perchè il metodo non deve restituire niente
	@Transactional
	public void deleteLibroById(Long id) {
		if (id == null) {
			log.warn("Tentativo eliminazione con id null");
			throw new IllegalArgumentException("Id non può essere vuoto");
		}
		log.info("Tentativo eliminazione libro con id {}", id);
		libroRepository.deleteById(id);
		log.info("Libro con id {} eliminato con successo", id);
	}

	@Transactional
	public LibroDTO updateLibro(Long id, LibroDTO libroDto) {
		if (libroDto == null || id == null) {
			log.warn("Aggiornamento con dati null - id: {}", id);
			throw new IllegalArgumentException("Id o Libro non trovati");
		}
		log.info("Aggiornamento libro con id: {}",id);
		Optional<Libro> libroOpt = libroRepository.findById(id);
		if(libroOpt.isEmpty()) {
			log.error("Libro non trovato per aggiornamento con id: {}", id);
			throw new IllegalArgumentException("Libro non trovato");
		}
		Libro libro = libroOpt.get();
		libro.setTitolo(libroDto.getTitolo());
		libro.setAutore(libroDto.getAutore());
		libro.setGenere(libroDto.getGenere());
		Libro libroAggiornato = libroRepository.save(libro);
		log.info("Libro con id: {} aggiornato con successo",id);
		return libroMapper.toDto(libroAggiornato);
	}
}