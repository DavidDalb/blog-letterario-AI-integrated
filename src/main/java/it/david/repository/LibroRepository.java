package it.david.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.david.model.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
	
	List<Libro> findByAutoreContainingIgnoreCase(String autore);
	
	List<Libro> findByTitoloContainingIgnoreCase(String titolo);
	
	List<Libro> findAllByGenereContainingIgnoreCase(String genere);

	Libro findByTitoloAndAutore(String titolo, String autore);

}
