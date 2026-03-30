package it.david.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.david.model.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long>{
	
	Page<Utente> findAll(Pageable pageable);
	
	Page<Utente> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
	
	boolean existsByEmail(String email);
	
	// Verifica se l'email è già usata da altri utenti, IdNot esclude L'id stesso, altrimenti sarebbe true con se stesso.
	boolean existsByEmailAndIdNot(String email, Long id);
}
