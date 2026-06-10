# BUILD STAGE
FROM gradle:jdk17-alpine AS resource-service-build

WORKDIR /app

COPY gradlew resource-service/build.gradle ./
COPY gradle ./gradle
RUN ["./gradlew", "dependencies", "--no-daemon"]

COPY resource-service/src ./src
RUN ["./gradlew", "assemble", "--no-daemon"]

# RUNTIME STAGE
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=resource-service-build --exclude=*-plain.jar /app/build/libs/*.jar ./app.jar

EXPOSE 8080

CMD ["java", "-jar", "./app.jar"]