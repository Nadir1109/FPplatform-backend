# Gebruik een Java 17 image als basis
FROM openjdk:17-jdk-slim

# Stel de werkdirectory in
WORKDIR /app

# Kopieer de Maven output (vervang door Gradle als je dat gebruikt)
COPY target/freelance-platform-0.0.1-SNAPSHOT.jar app.jar


# Expose de standaard poort waarop Spring Boot draait
EXPOSE 8080

# Voer de applicatie uit
ENTRYPOINT ["java", "-jar", "app.jar"]
