package it.david.exceptions;

import java.time.LocalDateTime;

//Classe POJO (DTO) per gli errori che andranno in un JSON leggibile
public class ErrorResponse {

	private int status;
	private String message;
	private LocalDateTime timestamp;
	
	public ErrorResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}

	//Getters. Spring li usa per dare JSON  (NON ha bisogno dei setter)
	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	
	
	
}
