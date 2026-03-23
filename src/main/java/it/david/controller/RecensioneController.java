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

import it.david.dto.RecensioneDTO;
import it.david.service.LibroService;
import it.david.service.RecensioneService;

@RestController
@RequestMapping("/api/v1/recensioni")
public class RecensioneController {

    private final LibroService libroService;

	private final RecensioneService recensioneService;
	
	public RecensioneController(RecensioneService recensioneService, LibroService libroService) {
		this.recensioneService = recensioneService;
		this.libroService = libroService;
		
	}
	@GetMapping
	public ResponseEntity<List<RecensioneDTO>> getAll() {
		List<RecensioneDTO> recensioni = recensioneService.findAll();
		return ResponseEntity.ok(recensioni);
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<RecensioneDTO> getById(@PathVariable Long id) {
		RecensioneDTO recensione = recensioneService.findById(id);
		return ResponseEntity.ok(recensione);
	}
	@GetMapping("/search/valutazione-gte")
	public ResponseEntity<List<RecensioneDTO>> searchByStars(@RequestParam int valutazioneStelle) {
		List<RecensioneDTO> recensioni = recensioneService.findByValutazioneStelleGreaterThanEqual(valutazioneStelle);
		return ResponseEntity.ok(recensioni);
	}
	 @PostMapping
	 public ResponseEntity<RecensioneDTO> createRecensione(@RequestBody RecensioneDTO recensioneDto) {
		 RecensioneDTO recensione = recensioneService.saveRecensione(recensioneDto);
		 return new ResponseEntity<>(recensione, HttpStatus.CREATED);
	 }
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRecensione(@PathVariable Long id) {
		recensioneService.deleteRecensioneById(id);
		return ResponseEntity.noContent().build();
	}
	@PutMapping("/{id}")
	public ResponseEntity<RecensioneDTO> updateRecensione(@PathVariable Long id, @RequestBody RecensioneDTO recensioneDto) {
		RecensioneDTO recensione = recensioneService.updateRecensione(id, recensioneDto);
		return ResponseEntity.ok(recensione);
	}
	
}
