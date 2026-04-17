# Blog Letterario con Integrazione AI

[![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue?style=for-the-badge&logo=docker)](https://www.docker.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)

Un'applicazione backend moderna che combina la gestione di un blog letterario con la potenza dell'Intelligenza Artificiale locale. Questo progetto dimostra competenze nell'integrazione di **LLM (Large Language Models)**, sicurezza **JWT**, Containerizzazione e gestione del ciclo di vita dei servizi (App, DB, AI) tramite **Docker**.

---

## Caratteristiche Principali

- **Generazione Recensioni AI**: Integrazione con **Ollama** (locale) per generare recensioni automatiche basate sul contenuto dei libri (modello `llama3.2:1b`).
- **Sicurezza Robusta**: Autenticazione Stateless tramite **JWT (JSON Web Token)** con gestione dei ruoli (`UTENTE`, `AMMINISTRATORE`).
- **Infrastruttura Containerizzata**: Deployment orchestrato con **Docker Compose** e **Multi-stage Build** per immagini leggere e sicure.
- **Persistenza e Migrazioni**: Database PostgreSQL con gestione del versionamento dello schema tramite **Flyway**.
- **Documentazione Interattiva**: Esplorazione completa degli endpoint tramite **Swagger UI (OpenAPI 3)**.
- **Osservabilità**: Monitoraggio dello stato dei servizi tramite **Spring Boot Actuator**.

---

## Tecnologie Utilizzate

- **Backend**: Java 21, Spring Boot 3.4
- **AI Integration**: Spring AI, Ollama
- **Database**: PostgreSQL, Spring Data JPA, Flyway
- **Security**: Spring Security, JWT
- **DevOps**: Docker, Docker Compose
- **Documentazione**: OpenAPI 3 (Swagger UI)

---

## Installazione e Avvio Rapido

Grazie all'integrazione Docker, non è necessario installare database o strumenti di build localmente.

### 1. Clonazione del repository
```bash
git clone https://github.com/DavidDalb/blog-letterario-AI-integrated.git
cd BlogLetterarioAI
```

### 2. Avvio dell'infrastruttura
Lancia il comando per compilare l'applicazione (Multi-stage Build) e avviare tutti i servizi (App, DB, AI):
```bash
docker-compose up -d --build
```

### 3. Inizializzazione del modello AI (Una tantum)
Scarica il modello Llama 3.2:1b all'interno del container Ollama per abilitare le funzionalità AI:
```bash
docker exec -it ollama-service ollama pull llama3.2:1b
```
---
### Testing
L'applicazione include un DataLoader che popola automaticamente il sistema con dati di test all'avvio.

Swagger UI: http://localhost:8080/swagger-ui.html
Health Check: http://localhost:8080/actuator/health

### Credenziali di Test:
username: David
password: password
(Ruolo utente)

### Procedura per il test:
Effettua una POST a /api/auth/login con le credenziali sopra.
Copia il token dalla risposta.
Clicca sul tasto "Authorize" in Swagger e inserisci <IL_TUO_TOKEN>.
Testa l'endpoint AI: /api/libri/{id}/recensioni/ai inserendo l'id del libro (id= 1)

### Dettagli Tecnici (Environment)
Le variabili di configurazione (password DB, segreti JWT) non sono state gestite nell'applicazione come variabili d'ambiente per favorire casi di Testing.

### Roadmap Blog letterario Ai integrated:
- Integrazione Vettoriale: Implementazione di pgvector su PostgreSQL 
- Embedding Pipeline: Utilizzo di modelli di embedding tramite Spring AI per trasformare testi in vettori numerici.
- RAG Context Provider: Sviluppo della logica per recuperare i pezzi di libro più pertinenti dal Vector Store e "istruire" l'LLM prima della generazione.

### Contatti
Davide Dalbenzio - dalbenziodavide01@gmail.com
