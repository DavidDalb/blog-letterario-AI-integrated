package it.david.security.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.david.model.Utente;
import it.david.repository.UtenteRepository;

@Service
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService{ //firma il contratto con Spring Security
	
	private final UtenteRepository utenteRepository;

	public UserDetailsServiceImpl(UtenteRepository utenteRepository) {
		super();
		this.utenteRepository = utenteRepository;
	}

	@Override         //Metodo convenzionale
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		var utente = utenteRepository.findByEmail(email)
	     .orElseThrow(() -> new UsernameNotFoundException("Email non trovata"));
		
		List<SimpleGrantedAuthority> authorities = utente.getRuoli().stream()
				.map(ruolo -> new SimpleGrantedAuthority(ruolo.getRuolo().name()))
				.toList();
		
		return new User( //org.springframework.security.core.userdetails.
			utente.getEmail(),
			utente.getPassword(),
			authorities) ;
	}

	
			
}
