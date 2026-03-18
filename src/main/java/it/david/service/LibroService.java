package it.david.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.david.model.Libro;
import it.david.repository.LibroRepository;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    //RuntimeException è usata per errori di esecuzione
    public List<Libro> findAll() {
        List<Libro> libri = libroRepository.findAll();
        if (libri.isEmpty()) {
            throw new RuntimeException("Nessun libro trovato");
        }
        return libri;
    }

    //IllegalArgumentException è usata per errori di validazione dei dati
    public Optional<Libro> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id non può essere vuoto");
        }
        return libroRepository.findById(id);
    }

    public List<Libro> findByAutore(String autore) {
        if (autore == null || autore.isEmpty()) {
            throw new IllegalArgumentException("Autore non può essere vuoto");
        }
        return libroRepository.findByAutoreContainingIgnoreCase(autore);
    }

    public List<Libro> findByTitolo(String titolo) {
        if (titolo == null || titolo.isEmpty()) {
            throw new IllegalArgumentException("Titolo non può essere vuoto");
        }
        return libroRepository.findByTitoloContainingIgnoreCase(titolo);
    }

    public List<Libro> findByGenere(String genere) {
        if (genere == null || genere.isEmpty()) {
            throw new IllegalArgumentException("Genere non può essere vuoto");
        }
        return libroRepository.findAllByGenereContainingIgnoreCase(genere);
    }

    public Libro save(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("Libro non può essere vuoto");
        }
        return libroRepository.save(libro);
    }

    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id non può essere vuoto");
        }
        libroRepository.deleteById(id);
    }
}
