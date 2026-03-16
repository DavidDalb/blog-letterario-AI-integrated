package it.david.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.david.model.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long>{
	
	List<Utente> findByUsernameContainingIgnoreCase(String username);
	
	boolean existsByEmail(String email);
}
