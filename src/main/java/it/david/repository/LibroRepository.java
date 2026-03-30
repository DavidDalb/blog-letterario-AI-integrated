package it.david.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.david.model.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
	
	//(Spring) la pagination avviene sugli elementi List. List -> Page     Pageable contiene le istruzioni ricevute dal Controller
	Page<Libro> findByAutoreContainingIgnoreCase(String autore,Pageable pageable);
	
	Page<Libro> findByTitoloContainingIgnoreCase(String titolo,Pageable pageable);
	
	Page<Libro> findAllByGenereContainingIgnoreCase(String genere,Pageable pageable);

	Libro findByTitoloAndAutore(String titolo, String autore);
}