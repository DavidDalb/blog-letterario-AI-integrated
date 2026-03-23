package it.david.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.david.model.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long>{
	
	List<Utente> findByUsernameContainingIgnoreCase(String username);
	
	boolean existsByEmail(String email);
	
	// Verifica se l'email è già usata da altri utenti, IdNot esclude L'id stesso, altrimenti sarebbe true con se stesso.
	boolean existsByEmailAndIdNot(String email, Long id);
}
