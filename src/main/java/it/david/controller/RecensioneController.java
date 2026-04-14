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

import it.david.dto.RecensioneDTO;
import it.david.service.LibroService;
import it.david.service.RecensioneService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/recensioni")
public class RecensioneController {

    private final RecensioneService recensioneService;
	
	public RecensioneController(RecensioneService recensioneService, LibroService libroService) {
		this.recensioneService = recensioneService;
		
	}
	@GetMapping
	public ResponseEntity<Page<RecensioneDTO>> getAll(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
		
		Page<RecensioneDTO> recensioni = recensioneService.findAll(pageable);
		return ResponseEntity.ok(recensioni);
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<RecensioneDTO> getById(@PathVariable Long id) {
		RecensioneDTO recensione = recensioneService.findById(id);
		return ResponseEntity.ok(recensione);
	}
	@GetMapping("/search/valutazione-gte")
	public ResponseEntity<Page<RecensioneDTO>> searchByStars(
			@RequestParam int valutazioneStelle,
			@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
		
		Page<RecensioneDTO> recensioni = recensioneService.findByValutazioneStelleGreaterThanEqual(valutazioneStelle, pageable);
		return ResponseEntity.ok(recensioni);
	}
	 @PostMapping
	 @PreAuthorize("hasRole('UTENTE')")
	 public ResponseEntity<RecensioneDTO> createRecensione(@Valid @RequestBody RecensioneDTO recensioneDto) {
		 RecensioneDTO recensione = recensioneService.saveRecensione(recensioneDto);
		 return new ResponseEntity<>(recensione, HttpStatus.CREATED);
	 }
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('AMMINISTRATORE')")
	public ResponseEntity<Void> deleteRecensione(@PathVariable Long id) {
		recensioneService.deleteRecensioneById(id);
		return ResponseEntity.noContent().build();
	}
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('AMMINISTRATORE')")
	public ResponseEntity<RecensioneDTO> updateRecensione(@PathVariable Long id, @Valid @RequestBody RecensioneDTO recensioneDto) {
		RecensioneDTO recensione = recensioneService.updateRecensione(id, recensioneDto);
		return ResponseEntity.ok(recensione);
	}
	
}
