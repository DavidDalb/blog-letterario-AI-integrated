package it.david.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ruoli")
public class Ruolo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING) //Converte la Enum in stringa Esatta nel DB Es. ROLE_UTENTE
	private Eruolo ruolo;

	public Ruolo(Long id, Eruolo ruolo) {
		this.id = id;
		this.ruolo = ruolo;
	}

	public Eruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Eruolo ruolo) {
		this.ruolo = ruolo;
	}

	public Long getId() {
		return id;
	}
	
	

}
