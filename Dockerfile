# Step 1: Use a base image with JDK 17
FROM openjdk:17-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the built jar file from the target directory to the working directory
COPY target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar

# Step 4: Expose the port your Spring Boot application will run on
EXPOSE 9999

# Step 5: Run the jar file
ENTRYPOINT ["java", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]