package it.david.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import it.david.dto.LibroDTO;
import it.david.mapper.LibroMapper;
import it.david.model.Libro;
import it.david.repository.LibroRepository;

@ExtendWith(MockitoExtension.class)  //per attivare il supporto di Mockito in questa classe
public class LibroServiceTest {

	@Mock //SOSIA: Crea un finto database
	private LibroRepository libroRepository;
	
	@Mock //SOSIA: Crea un finto mapper
	private LibroMapper libroMapper;
	
	@InjectMocks //VERO DA TESTARE: Crea il vero service per iniettare i Mock finti sopra
	private LibroService libroService;
	
	@Test
	void findById_dovrebbeRestituireUnLibro_quandoEsiste() {
		
		//GIVEN: Prepariamo i dati per istruire i sosia
		Long id = 1L;
	    Libro libro = new Libro();
	    libro.setId(id);
	    libro.setTitolo("Il Gattopardo");

	    LibroDTO dto = new LibroDTO();
	    dto.setId(id);
	    dto.setTitolo("Il Gattopardo");
	    
	    //ISTRUZIONE AL SOSIA DB
	    // "Quando ricevi la chiamata findById(1), rispondi con la scatola (Optional) contenente il libroFinto"
	    when(libroRepository.findById(1l)).thenReturn(Optional.of(libro));
	    // Diciamo al Mapper: "Se il Service ti dà il libro finto, trasformalo subito nel DTO finto"
	    when(libroMapper.toDto(libro)).thenReturn(dto);
	
	    //Chiamiamo il metodo VERO del Service. 
	    // Lui crederà di parlare con un Database vero, ma parlerà con i sosia istruiti sopra.
	    LibroDTO result = libroService.findById(id);
	    
	    // Verifichiamo che il "risultato" che ci ha dato il Service sia quello che ci aspettavamo
	    assertNotNull(result, "Errore: Il Service ha restituito null!");
	    assertEquals("Il Gattopardo", result.getTitolo(), "Errore: Il titolo non corrisponde!");
	    
	    // Verifichiamo che il Service abbia effettivamente 'disturbato' il database una volta
	    verify(libroRepository, times(1)).findById(id);
	    
		
	}
	
	@Test
	void findById_quandoNonEsisteUnLibro_lanciaEccezione() {
		//Necessario solo l'id perchè non restituisce un empty il test
		Long id = 1L;
		
		when(libroRepository.findById(1L)).thenReturn(Optional.empty());
		
		assertThrows(IllegalArgumentException.class, () -> {libroService.findById(id);
	});
		
		}}
