package it.david.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.david.dto.UtenteDTO;
import it.david.dto.auth.JwtResponse;
import it.david.dto.auth.LoginRequest;
import it.david.dto.auth.RegisterRequest;
import it.david.service.UtenteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
	
	private UtenteService utenteService;

	public AuthController(UtenteService utenteService) {
		super();
		this.utenteService = utenteService;
	}
	@PostMapping("/register")
	public ResponseEntity<UtenteDTO> signUp(@Valid @RequestBody RegisterRequest registrationDto) {
		
		UtenteDTO risultato = utenteService.register(registrationDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(risultato);
	
	}

		@PostMapping("/login")
		public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody LoginRequest loginDto) {
			
			JwtResponse jwtResponse = utenteService.login(loginDto);
			
			return ResponseEntity.ok(jwtResponse);
		}

}
