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

    public List<Libro> findAll() {
        return libroRepository.findAll();
    }

    public Optional<Libro> findById(Long id) {
        return libroRepository.findById(id);
    }

    public List<Libro> findByAutoreContainingIgnoreCase(String autore) {
        return libroRepository.findByAutoreContainingIgnoreCase(autore);
    }

    public List<Libro> findByTitoloContainingIgnoreCase(String titolo) {
        return libroRepository.findByTitoloContainingIgnoreCase(titolo);
    }

    public List<Libro> findAllByGenereContainingIgnoreCase(String genere) {
        return libroRepository.findAllByGenereContainingIgnoreCase(genere);
    }

    public Libro save(Libro libro) {
        return libroRepository.save(libro);
    }

    public void deleteById(Long id) {
        libroRepository.deleteById(id);
    }
}
