# Étape de build
FROM maven:3.9.14-eclipse-temurin-25 AS build
WORKDIR /app

# Copier les fichiers de configuration Maven
COPY pom.xml .
# Télécharger les dépendances (mise en cache)
RUN mvn dependency:go-offline -B

# Copier le code source et construire l'application
COPY src ./src
RUN mvn clean package -DskipTests

# Étape d'exécution
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copier le JAR construit depuis l'étape précédente
COPY --from=build /app/target/*.jar app.jar

# Exposer le port de l'application
EXPOSE 8080

# Définir les variables d'environnement par défaut
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_SQL_INIT_MODE=never
ENV ALLOW_ORIGIN=https://oskarz.nastream.fr

# Commande de démarrage
ENTRYPOINT ["java", "-jar", "app.jar"]
