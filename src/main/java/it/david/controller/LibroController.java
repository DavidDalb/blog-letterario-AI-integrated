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

import it.david.dto.LibroDTO;
import it.david.service.LibroService;
import jakarta.validation.Valid;

//Rest controller per restituire dati json
@RestController
@RequestMapping("/api/v1/libri")
public class LibroController {

	private final LibroService libroService;

	public LibroController(LibroService libroService) {
		this.libroService = libroService;
	}

	@GetMapping // Userà l'url di default
	//@PreAuthorize("hasRole('AMMINISTRATORE')")  //Commented: GESTITO DA PERMIT GLOBALE (WEBSECURITYCONFIG)
	public ResponseEntity<Page<LibroDTO>> getAll(@PageableDefault(page = 0, size = 10, sort = "titolo") Pageable pageable) {
		Page<LibroDTO> libri = libroService.findAll(pageable);
		// Manda una risposta codice 200  
		return ResponseEntity.ok(libri);
	}

	@GetMapping("/{id}")
	//@PreAuthorize("hasAnyRole('UTENTE', 'AMMINISTRATORE')")   //Commented: GESTITO DA PERMIT GLOBALE (WEBSECURITYCONFIG)
	public ResponseEntity<LibroDTO> getById(@PathVariable Long id) {
		LibroDTO libro = libroService.findById(id);
		return ResponseEntity.ok(libro);
	}

	@GetMapping("/search/autore")                      // api/v1/libri/search?autore=...   //'autore' in entrata è name convention
	public ResponseEntity<Page<LibroDTO>> searchByAutore(@RequestParam String autore, @PageableDefault(size = 10) Pageable pageable) {
		Page<LibroDTO> libri = libroService.findByAutoreContainingIgnoreCase(autore, pageable);
		return ResponseEntity.ok(libri);
	}
	@GetMapping("/search/titolo")
	public ResponseEntity<Page<LibroDTO>> searchByTitolo(@RequestParam String titolo, @PageableDefault(size = 10) Pageable pageable) {
		Page<LibroDTO> libri = libroService.findByTitoloContainingIgnoreCase(titolo, pageable);
		return ResponseEntity.ok(libri);
}
	@GetMapping("/search/genere")
	public ResponseEntity<Page<LibroDTO>> searchByGenere(@RequestParam String genere, @PageableDefault(size = 10) Pageable pageable) {
		Page<LibroDTO> libri = libroService.findByGenereContainingIgnoreCase(genere, pageable);
		return ResponseEntity.ok(libri);

	}
	@PostMapping                              //@Valid Serve a Validare con i parametri Validation dei DTO. @RequestBody Per accettare JSON 
	@PreAuthorize("hasRole('AMMINISTRATORE')")
	public ResponseEntity<LibroDTO> createLibro(@Valid @RequestBody LibroDTO libroDto) {
		LibroDTO libro = libroService.saveLibro(libroDto);
		
		// Uso 'new ResponseEntity' perché voglio restituire il corpo del libro con lo stato 201 senza l'obbligo
		// di generare l'URI (Verboso) richiesto dal metodo statico .created().
		return new ResponseEntity<>(libro, HttpStatus.CREATED);   //HTTP Stato 201
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('AMMINISTRATORE')")
	                    //in <> non viaggerà niente 
	public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
		libroService.deleteLibroById(id);
		
		                    //noContent -> Stato 204 HTTP
		return ResponseEntity.noContent().build();       //.build() è necessario perché il corpo è vuoto (Void)
	                                                     // Notifica a Spring che la risposta è terminata.
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('AMMINISTRATORE')")
	public ResponseEntity<LibroDTO> updateLibro(@PathVariable Long id, @Valid @RequestBody LibroDTO libroDto) {
		
		LibroDTO result = libroService.updateLibro(id, libroDto);
		
		return ResponseEntity.ok(result);
	}
	
}

