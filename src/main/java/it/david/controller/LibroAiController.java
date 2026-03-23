package it.david.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.david.service.AiService;

@RestController
@RequestMapping("/api/v1/libri")
public class LibroAiController {
	
	private final AiService aiService;

	public LibroAiController(AiService aiService) {
		this.aiService = aiService;
	}

	@GetMapping("/{id}/recensione-ai")
	public ResponseEntity<String> generaRecensione(@PathVariable Long id) {
		String recensione = aiService.generaRecensioneLibro(id);
		
		return ResponseEntity.ok(recensione);
		
	}
	
}
