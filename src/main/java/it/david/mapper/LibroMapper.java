package it.david.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.david.dto.LibroDTO;
import it.david.model.Libro;

@Mapper(componentModel = "spring")
public interface LibroMapper {

    // Istanza del mapper (se non si usa Spring)
    // LibroMapper INSTANCE = Mappers.getMapper(LibroMapper.class);

    LibroDTO toDto(Libro entity);

    @Mapping(target = "recensioni",ignore = true)
    Libro toEntity(LibroDTO dto);
    
    //Converte intere liste
    List<LibroDTO> toDtoList(List<Libro> libri);
}
