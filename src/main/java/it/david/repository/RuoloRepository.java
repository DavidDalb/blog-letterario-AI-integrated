package it.david.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.david.model.Eruolo;
import it.david.model.Ruolo;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Long> {
	
	Optional<Ruolo> findByRuolo(Eruolo ruolo);

}
