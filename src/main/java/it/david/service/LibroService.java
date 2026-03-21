package it.david.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.david.dto.LibroDTO;
import it.david.mapper.LibroMapper;
import it.david.model.Libro;
import it.david.repository.LibroRepository;
                                                             //*SNELLIRE IL CODICE (prendi spunto da recensioneService)
@Service
public class LibroService {

	private final LibroRepository libroRepository;
	private final LibroMapper libroMapper;

	// INJECTION by constructor
	public LibroService(LibroRepository libroRepository, LibroMapper libroMapper) {
		this.libroRepository = libroRepository;
		this.libroMapper = libroMapper;
	}

	// RuntimeException è usata per errori di esecuzione
	public List<LibroDTO> findAll() {
		List<Libro> libri = libroRepository.findAll();
		if (libri.isEmpty()) {
			throw new RuntimeException("Nessun libro trovato");
		}
		return libroMapper.toDtoList(libri);
	}

	// IllegalArgumentException è usata per errori di validazione dei dati
	public LibroDTO findById(Long id) {
		Optional<Libro> libroOpt = libroRepository.findById(id);

		if (libroOpt.isEmpty()) {
			throw new IllegalArgumentException("Id non può essere vuoto");
		}
		Libro libro = libroOpt.get();
		return libroMapper.toDto(libro);
	}

	// isEmpty è per i contenitori vuoti
	public List<LibroDTO> findByAutoreContainingIgnoreCase(String autore) {
		if (autore == null || autore.isEmpty()) {
			throw new IllegalArgumentException("Autore non può essere vuoto");
		}
		List<Libro> risultato = libroRepository.findByAutoreContainingIgnoreCase(autore);
		return libroMapper.toDtoList(risultato);
	}

	public List<LibroDTO> findByTitoloContainingIgnoreCase(String titolo) {
		if (titolo == null || titolo.isEmpty()) {
			throw new IllegalArgumentException("Titolo non può essere vuoto");
		}
		List<Libro> risultato = libroRepository.findByTitoloContainingIgnoreCase(titolo);
		return libroMapper.toDtoList(risultato);
	}

	public List<LibroDTO> findByGenereContainingIgnoreCase(String genere) {
		if (genere == null || genere.isEmpty()) {
			throw new IllegalArgumentException("Genere non può essere vuoto");
		}
		List<Libro> risultato = libroRepository.findAllByGenereContainingIgnoreCase(genere);
		return libroMapper.toDtoList(risultato);
	}

	@Transactional
	public LibroDTO saveLibro(LibroDTO libroDto) {
		if (libroDto == null) {
			throw new IllegalArgumentException("Libro non può essere vuoto");
		}
		Libro libroEsistente = libroRepository.findByTitoloAndAutore(libroDto.getTitolo(), libroDto.getAutore());
		if (libroEsistente != null) {
			throw new IllegalArgumentException("Libro già esistente");
		}
		//Da DTO ad Entity perchè non ha ancora un Id e il repo accetta solo entity 'Libro'
		Libro libroDaSalvare = libroMapper.toEntity(libroDto);
		//Salvando in questo modo ottiene un Id
		Libro libroSalvato = libroRepository.save(libroDaSalvare);
		
		return libroMapper.toDto(libroSalvato);
		}
		

	//DTO non necessario perchè il metodo non deve restituire niente
	public void deleteLibroById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Id non può essere vuoto");
		}
		libroRepository.deleteById(id);
	}

	@Transactional
	public LibroDTO updateLibro(Long id, LibroDTO libroDto) {
		if (libroDto == null || id == null) {
			throw new IllegalArgumentException("Id o Libro non trovati");
		}
		Optional<Libro> libroOpt = libroRepository.findById(id);
		if(libroOpt.isEmpty()) {
			throw new IllegalArgumentException("Libro non trovato");
		}
		Libro libro = libroOpt.get();
		libro.setTitolo(libroDto.getTitolo());
		libro.setAutore(libroDto.getAutore());
		libro.setGenere(libroDto.getGenere());
		Libro libroAggiornato = libroRepository.save(libro);
		return libroMapper.toDto(libroAggiornato);
	}
}