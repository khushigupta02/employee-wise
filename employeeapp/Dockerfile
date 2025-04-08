# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file into the container
COPY target/employeeapp-0.0.1-SNAPSHOT.jar.original app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
