# Oskar Game Back

## Présentation du projet
Oskar Game est une application de jeu de prédictions pour les cérémonies des Oscars. Ce projet constitue la partie backend de l'application, développée avec Spring Boot.

## Prérequis
- Java 25
- Maven
- Docker et Docker Compose

## Installation et Démarrage

### 1. Démarrer la base de données
L'application utilise une base de données PostgreSQL. Un fichier `docker-compose.yml` est fourni pour configurer rapidement l'environnement.

```bash
docker compose up -d
```

La base de données sera accessible sur `localhost:5432` avec les identifiants suivants (définis dans `application.properties`) :
- **Base de données** : `oskar_game`
- **Utilisateur** : `postgres`
- **Mot de passe** : `postgres`

### 2. Démarrer l'application Spring Boot
Une fois la base de données lancée, vous pouvez démarrer l'application :

```bash
./mvnw spring-boot:run
```

L'application sera accessible sur `http://localhost:8080`.

## Configuration
Les propriétés de l'application se trouvent dans `src/main/resources/application.properties`. 
Le projet est configuré pour initialiser automatiquement les données à partir du fichier `src/main/resources/data.sql` au démarrage (`spring.sql.init.mode=always`).

## Fonctionnalités principales
- Gestion des salons de jeu (Rooms)
- Gestion des catégories et des films
- Communication en temps réel via WebSockets
