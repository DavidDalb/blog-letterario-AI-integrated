package it.david.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import it.david.model.Libro;
import it.david.model.Recensione;
import java.util.List;


@Repository
public interface RecensioneRepository extends JpaRepository<Recensione, Long> {
	
	List<Recensione> findByLibroId(Libro libroId);
	
	Page<Recensione> findByValutazioneStelleGreaterThanEqual(int valutazioneStelle, Pageable pageable);
	
	

}
