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

import it.david.dto.LibroDTO;
import it.david.service.LibroService;

//Rest controller per restituire dati json
@RestController
@RequestMapping("/api/v1/libri")
public class LibroController {

	private final LibroService libroService;

	public LibroController(LibroService libroService) {
		this.libroService = libroService;
	}

	@GetMapping // Userà l'url di default
	public ResponseEntity<List<LibroDTO>> getAll() {
		List<LibroDTO> libri = libroService.findAll();
		// Analogamente come un system.out.print ma es. per un frontendista
		return ResponseEntity.ok(libri);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LibroDTO> getById(@PathVariable Long id) {
		LibroDTO libro = libroService.findById(id);
		return ResponseEntity.ok(libro);
	}
                                 
	@GetMapping("/search/autore")                      // api/v1/libri/search?autore=...   //'autore' in entrata è name convention
	public ResponseEntity<List<LibroDTO>> searchByAutore(@RequestParam String autore) {
		List<LibroDTO> libri = libroService.findByAutoreContainingIgnoreCase(autore);
		return ResponseEntity.ok(libri);
	}
	@GetMapping("/search/titolo") 
	public ResponseEntity<List<LibroDTO>> searchByTitolo(@RequestParam String titolo) {
		List<LibroDTO> libri = libroService.findByTitoloContainingIgnoreCase(titolo);
		return ResponseEntity.ok(libri);
}
	@GetMapping("/search/genere") 
	public ResponseEntity<List<LibroDTO>> searchByGenere(@RequestParam String genere) {
		List<LibroDTO> libri = libroService.findByGenereContainingIgnoreCase(genere);
		return ResponseEntity.ok(libri);

	}
	@PostMapping                             //Per accettare JSON
	public ResponseEntity<LibroDTO> createLibro(@RequestBody LibroDTO libroDto) {
		LibroDTO libro = libroService.saveLibro(libroDto);
		
		// Uso 'new ResponseEntity' perché voglio restituire il corpo del libro con lo stato 201 senza l'obbligo
		// di generare l'URI (Verboso) richiesto dal metodo statico .created().
		return new ResponseEntity<>(libro, HttpStatus.CREATED);   //HTTP Stato 201
	}
	
	@DeleteMapping("/{id}")
	                    //in <> non viaggerà niente 
	public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
		libroService.deleteLibroById(id);
		
		                    //noContent -> Stato 204 HTTP
		return ResponseEntity.noContent().build();       //.build() è necessario perché il corpo è vuoto (Void)
	                                                     // Notifica a Spring che la risposta è terminata.
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<LibroDTO> updateLibro(@PathVariable Long id, @RequestBody LibroDTO LibroDto) {
		
		LibroDTO result = libroService.updateLibro(id, LibroDto);
		
		return ResponseEntity.ok(result);
	}
	
}

