FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged Spring Boot application JAR file into the container
COPY target/your-application.jar /app/eshop-service-1.0-SNAPSHOT.jar

# Command to run the Spring Boot application
CMD ["java", "-jar", "/app/eshop-service-1.0-SNAPSHOT.jar"]