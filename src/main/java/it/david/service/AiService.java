package it.david.service;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient; 
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import it.david.model.Libro;
import it.david.repository.LibroRepository;

@Service
public class AiService {

    private final ChatClient chatClient;
    private final LibroRepository libroRepository;

    
    public AiService(ChatClient.Builder chatClientBuilder, LibroRepository libroRepository) {
        this.chatClient = chatClientBuilder.build();
        this.libroRepository = libroRepository;
    }

    // Metodo generico
    public String generaMessaggio(String messaggio) {
        return chatClient.prompt()
                .user(messaggio)
                .call()
                .content();
    }

    public String generaRecensioneLibro(Long libroId) {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro con ID " + libroId + " non trovato"));

        String testoPrompt = """
                Agisci come un critico letterario.
                Analizza il libro "{titolo}" scritto da {autore}.
                Il genere dell'opera è {genere}.
                COMPITO: Genera una recensione professionale di 100 parole
                """;

        PromptTemplate template = new PromptTemplate(testoPrompt);
        Prompt promptPronto = template.create(Map.of(
                "titolo", libro.getTitolo(),
                "autore", libro.getAutore(),
                "genere", libro.getGenere()
        ));

        return chatClient.prompt(promptPronto)
                .call()
                .content();
    }
}