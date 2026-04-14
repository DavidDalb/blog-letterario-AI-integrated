package it.david.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.david.dto.UtenteDTO;
import it.david.dto.auth.RegisterRequest;
import it.david.model.Ruolo;
import it.david.model.Utente;

@Mapper(componentModel = "spring")
public interface UtenteMapper {
	
	UtenteDTO toDto(Utente entity);
	
	@Mapping(target = "id",ignore = true)        //L'id lo genera il DB
	@Mapping(target = "recensioni",ignore = true)
	@Mapping(target = "password",ignore = true)  //La password va hashata nel service
	@Mapping(target = "ruoli",ignore = true)     //i ruoli si cercano con il ruoloRepository nel service
	@Mapping(target = "email",ignore = true)     //per manipolazione case insensitive
	Utente toEntity(RegisterRequest registrationDto);
	
	@Mapping(target = "recensioni",ignore = true)
	@Mapping(target = "password",ignore = true)
	@Mapping(target = "ruoli",ignore = true)
	Utente toEntity(UtenteDTO utenteDto);
	
	List<UtenteDTO> toDtoList(List<Utente> utenti);

	//default permette ai metodi di interfacce di avere il body
	//Spiega a MapStruct come estrarre il nome dall'oggetto Ruolo.
	default String mapRuoloToString(Ruolo ruolo) {
		if(ruolo == null || ruolo.getRuolo() == null) {
			return null;
		}
		return ruolo.getRuolo().name();  //name prende la stringa da un Enum
	}
}
