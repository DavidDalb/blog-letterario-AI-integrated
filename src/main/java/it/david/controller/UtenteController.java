package it.david.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.david.dto.UtenteDTO;
import it.david.service.UtenteService;

@RestController
@RequestMapping("/api/v1/utenti")
public class UtenteController {
	
	private final UtenteService utenteService;

	public UtenteController(UtenteService utenteService) {
		this.utenteService = utenteService;
	}
	
	@GetMapping
	public ResponseEntity <List<UtenteDTO>> getAll() {
		List<UtenteDTO> utenti = utenteService.findAllUtenti();
		return ResponseEntity.ok(utenti);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UtenteDTO> getById(@PathVariable Long id) {
		UtenteDTO utente = utenteService.findUtenteById(id);
		
		return ResponseEntity.ok(utente);
	}

	@GetMapping("/search/username")
	public ResponseEntity<List<UtenteDTO>> searchByUsername(@RequestParam String username) {
		List<UtenteDTO> utenti = utenteService.findByUsernameContainingIgnoreCase(username);
		
		return ResponseEntity.ok(utenti);
	}
	@PostMapping
	public ResponseEntity<UtenteDTO> createUtente(@RequestBody UtenteDTO utenteDto) {
		UtenteDTO utente = utenteService.saveUtente(utenteDto);
		
		return new ResponseEntity<>(utente, HttpStatus.CREATED);
	}
	@PutMapping("/{id}")
	public ResponseEntity<UtenteDTO> updateUtente(@PathVariable Long id, @RequestBody UtenteDTO utenteDto) {
		UtenteDTO utente = utenteService.updateUtente(id, utenteDto);
		
		return ResponseEntity.ok(utente);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUtente(@PathVariable Long id) {
		utenteService.deleteUtenteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
