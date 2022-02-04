FROM adoptopenjdk/openjdk11:alpine-jre

# Add non-root user
RUN addgroup -g 1001 -S appuser && adduser -u 1001 -S appuser -G appuser

# Use that newly created user going forward.
USER appuser

# All file oriented copies from here will be relative to the path /app
WORKDIR /app

# Copy the jar from local machine into /app/app.jar
ADD build/libs/db-message-0.0.1.jar app.jar

# Metadata for container runtime to let users know that this app is exposing port 8080.
EXPOSE 8080

# Command for running the application.
CMD ["java", "-jar", "/app/app.jar"]