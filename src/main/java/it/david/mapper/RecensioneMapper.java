package it.david.mapper;

import it.david.dto.RecensioneDTO;
import it.david.model.Recensione;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecensioneMapper {

 
	 //Converte un Entity in DTO
	 //estrae solo gli ID dagli oggetti annidati
    @Mapping(target = "libroId", source = "libro.id")    // libro.getId() → libroId
    @Mapping(target = "utenteId", source = "autore.id")  // autore.getId() → utenteId
    RecensioneDTO toDto(Recensione entity);

    //ignore per ignorare i campi intenzionalmente
    @Mapping(target = "autore", ignore = true)
    @Mapping(target = "libro", ignore = true)
    Recensione toEntity(RecensioneDTO dto);     //<--Converte un DTO in Entity
    
    List<RecensioneDTO> toDtoList(List<Recensione> recensioni);
}

