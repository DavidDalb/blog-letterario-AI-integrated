package it.david.exception;

public class UtenteNotFoundException extends RuntimeException {
    public UtenteNotFoundException(String message) {
        super(message);
    }
}
