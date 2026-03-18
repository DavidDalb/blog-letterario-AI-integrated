package it.david.service;

import it.david.model.Utente;
import it.david.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;

    @Autowired
    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    public List<Utente> findAllUtenti() {
        return utenteRepository.findAll();
    }

    public Utente findUtenteById(Long id) {
        return utenteRepository.findById(id).orElseThrow(() -> new UtenteNotFoundException("Utente non trovato"));
    }

    public Utente saveUtente(Utente utente) {
        return utenteRepository.save(utente);
    }

    public void deleteUtente(Long id) {
        utenteRepository.deleteById(id);
    }
}


