package it.david.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/utenti")
@PreAuthorize("hasRole('AMMINISTRATORE')")  //Blocco a livello di classe
public class UtenteController {
	
	private final UtenteService utenteService;

	public UtenteController(UtenteService utenteService) {
		this.utenteService = utenteService;
	}
	
	@GetMapping
	public ResponseEntity <Page<UtenteDTO>> getAll(@PageableDefault(page = 0, size = 10, sort = "username") Pageable pageable) {
		Page<UtenteDTO> utenti = utenteService.findAllUtenti(pageable);
		return ResponseEntity.ok(utenti);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UtenteDTO> getById(@PathVariable Long id) {
		UtenteDTO utente = utenteService.findUtenteById(id);
		
		return ResponseEntity.ok(utente);
	}

	@GetMapping("/search/username")
	public ResponseEntity<Page<UtenteDTO>> searchByUsername(@RequestParam String username, @PageableDefault(size = 10) Pageable pageable) {
		Page<UtenteDTO> utenti = utenteService.findByUsernameContainingIgnoreCase(username, pageable);
		
		return ResponseEntity.ok(utenti);
	}
	@PostMapping
	public ResponseEntity<UtenteDTO> createUtente(@Valid @RequestBody UtenteDTO utenteDto) {
		UtenteDTO utente = utenteService.saveUtente(utenteDto);
		
		return new ResponseEntity<>(utente, HttpStatus.CREATED);
	}
	@PutMapping("/{id}")
	public ResponseEntity<UtenteDTO> updateUtente(@PathVariable Long id,@Valid @RequestBody UtenteDTO utenteDto) {
		UtenteDTO utente = utenteService.updateUtente(id, utenteDto);
		
		return ResponseEntity.ok(utente);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUtente(@PathVariable Long id) {
		utenteService.deleteUtenteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
