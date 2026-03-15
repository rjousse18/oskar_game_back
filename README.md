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

## TODO (front et back) (par ordre d'importance):

- DONE : Nouvelle page de résultats (prend en compte si on a les résultats ou si c'est juste le résumé de fin de partie)
- DONE : Voir si c'est possible d'essayer de rejoindre une game et si elle est terminé (in Progress + step = prediction.length), rediriger vers la page de résultats (en théorie ça devrait se faire tout seul, à vérifier)
- DONE : Changer les id des rooms pour qu'ils soient plus court (5 caractères lettres / chiffres)
- DONE : Modifier le css pour que la sélection de nommés prenne presque toute la place en mode téléphone
- DONE : Afficher le score board trié dans l'ordre décroissant
- DONE : Changer le order by de la query
- DONE : Faire une page d'administration pour rentrer les résultats des oscars.
- DONE : Gérer l'égalité des scores à l'affichage
- DONE : Modifier les boutons de prédiction pour avoir les films liés en dessous du nominé + en couleur plus légère
- DONE : Modifier le css pour harmoniser les textes, etc.
- DONE : AVANT MEP : variabiliser les host, bdd, etc (back et front) (cors, appel api, socket service)
- DONE : Affichage résultats en format téléphone
- Boutons bas de page en format téléphone
- DONE : Afficher le lien de la page de résultats pour que les gens puissent le copier facilement et revenir dessus une fois gagnants annoncés
- DONE : Affichage score en format téléphone pas terrible
- DONE : Faire les règles
- Lier le github (une fois l'historique nettoyé et la gestion du mode admin fait correctement)
- DONE : Annoncer sur twitter
- Harmoniser le code du front en divisant par composant (notamment la page Game)
- Trouver un moyen d'envoyer les erreurs du back au front pour les afficher
- Afficher des qr code pour rejoindre les games au lieu de juste le code.
- Polish (animation, couleur)
