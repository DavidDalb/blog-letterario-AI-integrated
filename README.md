# Blog Letterario con Integrazione AI

Questo progetto è un'applicazione Spring Boot che implementa un blog letterario completo, dove gli utenti possono gestire libri e recensioni. La caratteristica distintiva e innovativa è l'integrazione di un servizio di intelligenza artificiale (AI) che può generare recensioni di libri su richiesta, dimostrando capacità di integrazione con LLM (Large Language Models) locali. L'applicazione espone un'API RESTful sicura, ben documentata e robusta.

## Descrizione del Progetto

Il `Blog Letterario con Integrazione AI` è stato sviluppato con l'obiettivo di dimostrare competenze approfondite nello sviluppo backend, utilizzando il framework Spring Boot e le sue tecnologie correlate. Il sistema offre le seguenti funzionalità principali:

*   **Gestione Completa dei Libri**: Gli utenti possono creare, visualizzare, aggiornare ed eliminare i dettagli dei libri.
*   **Gestione delle Recensioni Manuali**: Possibilità di inserire recensioni per i libri, con un sistema di valutazione a stelle.
*   **Generazione di Recensioni tramite AI**: Utilizza l'integrazione con un Large Language Model (LLM) locale (tramite Ollama) per generare automaticamente recensioni per i libri, mostrando un'applicazione pratica dell'IA.
*   **Sicurezza Robusta (JWT)**: Implementazione di un sistema di autenticazione e autorizzazione basato su JSON Web Token (JWT), con ruoli utente (es. `UTENTE`, `AMMINISTRATORE`) e controllo degli accessi granulare a livello di endpoint e metodi.
*   **Validazione Dati Rigorosa**: Tutti i dati in ingresso vengono validati accuratamente per garantire l'integrità e la coerenza delle informazioni.

Questo progetto è ideale per mostrare una solida comprensione delle migliori pratiche di sviluppo software, inclusa la sicurezza, la persistenza dei dati, la validazione, l'integrazione con servizi esterni e la gestione centralizzata delle eccezioni.

## Funzionalità e Caratteristiche

*   **API RESTful**: Interfacce chiare e consistenti per interagire con le risorse del blog (Libri, Recensioni, Utenti).
*   **Spring Security & JWT**: Un'implementazione avanzata per proteggere gli endpoint, gestire l'autenticazione degli utenti e l'autorizzazione basata sui ruoli.
*   **Integrazione AI con Spring AI e Ollama**: Capacità di sfruttare modelli di intelligenza artificiale locali per generare contenuti dinamici (recensioni di libri), dimostrando l'integrazione con piattaforme AI moderne.
*   **Validazione dei Dati (DTO)**: Utilizzo di `@Valid` e annotazioni di validazione (es. `@NotBlank`, `@Size`, `@Email`, `@Min`, `@Max`) per mantenere l'integrità dei dati a livello di trasporto.
*   **Persistenza Dati con Spring Data JPA**: Gestione efficiente e relazionale del database PostgreSQL, con entità ben definite (es. `Libro`, `Recensione`, `Utente`, `Ruolo`).
*   **Database Migrations con Flyway**: Gestione controllata delle evoluzioni dello schema del database, garantendo che le modifiche siano applicate in modo affidabile e ripetibile.
*   **Gestione Globale delle Eccezioni**: Un meccanismo centralizzato per gestire le eccezioni e fornire risposte di errore coerenti e significative ai client API.
*   **Documentazione API Interattiva con OpenAPI (Swagger UI)**: Offre un'interfaccia utente grafica per esplorare, comprendere e testare tutti gli endpoint dell'API. mostra come l'API è strutturata e come interagire con essa.

## Tecnologie Utilizzate

*   **Backend**: Java 17+, Spring Boot 3
*   **Framework ORM**: Spring Data JPA, Hibernate
*   **Sicurezza**: Spring Security, JWT (JSON Web Tokens)
*   **Database**: PostgreSQL
*   **Gestione Dipendenze**: Apache Maven
*   **AI Integration**: Spring AI, Ollama (per modelli LLM locali come `llama3.2:1b`)
*   **Validazione**: Spring Validation (Jakarta Bean Validation)
*   **Documentazione API**: OpenAPI 3 (Swagger UI)
*   **Database Migrations**: Flyway
*   **Logging**: SLF4J con Logback

## Requisiti

Per eseguire il progetto in locale, avrai bisogno di:

*   **Java Development Kit (JDK) 17 o superiore**
*   **Apache Maven 3.6 o superiore**
*   **Docker** (fortemente consigliato per PostgreSQL e Ollama) oppure installazioni locali di:
    *   **PostgreSQL**: Un'istanza del database.
    *   **Ollama**: Installato e con il modello `llama3.2:1b` scaricato.

## Configurazione e Avvio Locale

Segui questi passaggi per configurare ed eseguire il progetto sulla tua macchina locale.

### 1. Clonare il Repository

```bash
git clone <https://github.com/DavidDalb/blog-letterario-AI-integrated.git>
cd BlogLetterarioAI
```

### 2. Configurazione del Database PostgreSQL

Assicurati di avere un'istanza PostgreSQL in esecuzione. Ti suggerisco di usare Docker per la sua semplicità:

```bash
docker run --name blog-db -e POSTGRES_DB=blog_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -p 5432:5432 -d postgres:13
```
Questo comando avvia un container PostgreSQL creando un database chiamato `blog_db` con utente `postgres` e password `root`, esposto sulla porta `5432`. Queste credenziali sono quelle configurate in `application.properties`.

### 3. Configurazione di Ollama (per l'AI)

Scarica e installa [Ollama](https://ollama.com/download) sul tuo sistema. Una volta installato e avviato il servizio Ollama, scarica il modello `llama3.2:1b` come specificato nella configurazione:

```bash
ollama run llama3.2:1b
```
Assicurati che Ollama sia in esecuzione e che il modello specificato sia disponibile. Di default, Ollama ascolterà su `http://localhost:11434`, come configurato nel progetto.

### 4. Verifica della Configurazione del Progetto

Il file `src/main/resources/application.properties` contiene tutte le configurazioni chiave. Ti prego di notare che le configurazioni fornite qui sotto sono già allineate con quelle presenti nel tuo file:

```properties
# Database PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/blog_db
spring.datasource.username=postgres
spring.datasource.password=root
spring.datasource.driver-class-name=org.postgresql.Driver

# Flyway Database Migrations (baseline-on-migrate è utile per database preesistenti)
spring.flyway.baseline-on-migrate=true

# Spring AI con Ollama
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=llama3.2:1b # Assicurati che questo modello sia stato scaricato in Ollama

# JWT (JSON Web Token)
app.david.jwtSecret=CHIAVE_DI_FIRMA_PER_TEST_LOCALE_PROGETTO_SECURITY_DAVID_LUNGA_64_CARATTERI # IMPORTANTISSIMO: In produzione, usa una variabile d'ambiente o un Vault!
app.david.jwtExpirationMs=86400000 # 24 ore in millisecondi
```
**Nota sulla Sicurezza**: La `jwtSecret` è attualmente nel file di configurazione per scopi di sviluppo locale. In un ambiente di produzione, questa chiave viene gestita in modo estremamente sicuro, preferibilmente tramite variabili d'ambiente o un servizio di gestione segreti (es. HashiCorp Vault, AWS Secrets Manager).

### 5. Esecuzione dell'Applicazione

Puoi avviare l'applicazione utilizzando Maven:

```bash
mvn spring-boot:run
```
L'applicazione sarà disponibile su `http://localhost:8080`.

## Endpoint API Principali e Accesso a Swagger UI

Una volta che l'applicazione è in esecuzione, la documentazione interattiva dell'API è accessibile tramite Swagger UI:

*   **Swagger UI**: `http://localhost:8080/swagger-ui.html`

**Come Accedere e Testare le API Protette da JWT con Swagger:**

1.  Apri l'URL di Swagger UI nel tuo browser.
2.  **Registrazione**: Utilizza l'endpoint `POST /api/auth/register` per registrare un nuovo utente. Puoi provare con un ruolo `UTENTE`.
3.  **Login**: Utilizza l'endpoint `POST /api/auth/login` per autenticarti. Ti verrà restituito un **JWT (JSON Web Token)**.
4.  **Autorizzazione in Swagger**:
    *   In cima alla pagina di Swagger, troverai un pulsante o un'icona **"Authorize"** (o un lucchetto). Cliccaci sopra.
    *   Inserisci il JWT che hai ottenuto dal login nel campo `Value`, preceduto dalla parola `Bearer ` (con uno spazio), ad esempio: `Bearer <IL_TUO_TOKEN_QUI>`.
    *   Clicca su "Authorize" e poi "Close".
5.  Ora potrai interagire con tutti gli endpoint protetti, Swagger invierà automaticamente il JWT in ogni richiesta, permettendoti di testare le funzionalità che richiedono autenticazione e specifici ruoli (es. creare un libro, aggiungere recensioni, generare recensioni AI).

### Esempi di Endpoint:

*   `POST /api/auth/register`: Registra un nuovo utente con username, email, password e ruoli.
*   `POST /api/auth/login`: Accedi al sistema e ottieni il tuo token JWT.
*   `GET /api/libri`: Recupera l'elenco di tutti i libri (pubblico).
*   `POST /api/libri`: Crea un nuovo libro (richiede JWT, ruolo `AMMINISTRATORE` o `UTENTE`).
*   `GET /api/libri/{libroId}`: Recupera i dettagli di un libro specifico.
*   `POST /api/libri/{libroId}/recensioni`: Aggiungi una recensione manuale a un libro (richiede JWT, ruolo `UTENTE`).
*   `GET /api/libri/{libroId}/recensioni/ai`: **Genera una recensione per un libro utilizzando l'AI** (richiede JWT, ruolo `UTENTE`). Questo endpoint interagirà con Ollama.

## Contatto

Per qualsiasi domanda o chiarimento sul progetto, non esitare a contattarmi:

*   **Email**: [dalbenziodavide01@gmail.com]
