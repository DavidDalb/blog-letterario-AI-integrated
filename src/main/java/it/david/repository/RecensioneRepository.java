package it.david.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.david.model.Libro;
import it.david.model.Recensione;
import java.util.List;


@Repository
public interface RecensioneRepository extends JpaRepository<Recensione, Long> {
	
	List<Recensione> findByLibroId(Libro libroId);
	
	List<Recensione> findByValutazioneStelleGreaterThanEqual(int valutazioneStelle);
	
	

}
