# Step 1:
FROM openjdk:17-jdk-alpine

# Step 2:
WORKDIR /app

# Step 3:
COPY target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar

# Step 4:
EXPOSE 9999

# Step 5:
ENTRYPOINT ["java", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]