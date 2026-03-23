package it.david.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.david.dto.UtenteDTO;
import it.david.model.Utente;

@Mapper(componentModel = "spring")
public interface UtenteMapper {
	
	UtenteDTO toDto(Utente entity);
	

	@Mapping(target = "recensioni",ignore = true)
	Utente toEntity(UtenteDTO dto);
	
	List<UtenteDTO> toDtoList(List<Utente> utenti);

}
