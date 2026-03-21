package it.david.mapper;

import it.david.dto.RecensioneDTO;
import it.david.model.Recensione;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecensioneMapper {

 
	//Converte un Entity in DTO
    RecensioneDTO toDto(Recensione entity);

    //Per ignorare i campi intenzionalmente
    @Mapping(target = "autore", ignore = true)
    @Mapping(target = "libro", ignore = true)
    Recensione toEntity(RecensioneDTO dto);     //<--Converte un DTO in Entity
    
    List<RecensioneDTO> toDtoList(List<Recensione> recensioni);
}

