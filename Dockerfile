# Step 1: Gebruik een OpenJDK base image
FROM openjdk:17-jdk-slim

# Step 2: Stel de werkmap in
WORKDIR /app

# Step 3: Kopieer het JAR-bestand naar de container
COPY target/freelance-platform-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Stel de poort in die door de applicatie wordt gebruikt
EXPOSE 8080

# Step 5: Start de applicatie
ENTRYPOINT ["java", "-jar", "app.jar"]
