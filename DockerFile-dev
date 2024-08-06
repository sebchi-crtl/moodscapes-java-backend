# Use the official Spring Boot base image for Java 17
FROM maven:3.8.4-openjdk-17 as build

# Set the working directory inside the container
WORKDIR /app


# Copy the pom.xml and download the dependencies
COPY pom.xml .
#RUN mvn dependency:go-offline

# Copy the source code
COPY src src

# Build the application
RUN mvn package -DskipTests

# Use a base image with Java to run the application
FROM maven:3.8.4-openjdk-17

# Set working directory
WORKDIR /app

# Copy the compiled Spring Boot JAR file into the container
COPY --from=build /app/target/moodscapes-backend-0.0.1-SNAPSHOT.jar moodscapes-backend.jar

VOLUME /tmp

# Expose the port that your Spring Boot application uses
EXPOSE 8070

# Command to run your application
CMD ["java", "-jar", "moodscapes-backend.jar"]
