package it.david.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import it.david.model.Libro;
import it.david.repository.LibroRepository;

@Service
public class AiService {
	
	//SLF4J: Log importantissimi poichè L'Ai può essere lenta e costosa (i log vengono inseriti nei service nei metodi)
	private static final Logger log = LoggerFactory.getLogger(AiService.class);

    private final ChatClient chatClient;
    private final LibroRepository libroRepository;

    // Spring AI inietta un Builder non inietta direttamente ChatClient
    public AiService(ChatClient.Builder chatClientBuilder, LibroRepository libroRepository) {
        this.chatClient = chatClientBuilder.build();   // .build() assembla il ChatClient con i dati in application.properties (api-key, modello ecc.)                                        
        this.libroRepository = libroRepository;
    }

    // Metodo generico
    public String generaMessaggio(String messaggio) {
    	log.info("Messaggio generico inviato all'AI: {}", messaggio);
        String risposta = chatClient.prompt()
        		.user(messaggio)  // messaggio utente
                .call()           // invia a Gemini
                .content();       // estrae solo il testo
        log.info("Risposta AI ricevuta per messaggio generico");
        return risposta;


    }

    //Prompt con segnaposti {}
    public String generaRecensioneLibro(Long libroId) {
    	log.info("Richiesta recensione AI per libro con id: {}", libroId);
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> {
                	log.error("Libro non trovato con id: {} — impossibile generare recensione", libroId);
                return new RuntimeException("Libro con ID " + libroId + " non trovato");
                });
        log.info("Generazione recensione AI per il libro '{}' di {}",libro.getTitolo(),libro.getAutore());
        String testoPrompt = """
                Agisci come un critico letterario.
                Analizza il libro "{titolo}" scritto da {autore}.
                Il genere dell'opera è {genere}.
                COMPITO: Genera una recensione professionale di 100 parole
                """;

     // Sostituisce i segnaposto con i valori reali del libro
        PromptTemplate template = new PromptTemplate(testoPrompt);
        Prompt promptPronto = template.create(Map.of(
                "titolo", libro.getTitolo(),
                "autore", libro.getAutore(),
                "genere", libro.getGenere()
        ));
         log.info("Invio prompt all'AI per libro: {}", libro.getTitolo());
        String recensione = chatClient.prompt(promptPronto)
                .call()
                .content();
        log.info("Recensione generata con successo per libro: {}", libro.getTitolo());
        return recensione;
    }
}