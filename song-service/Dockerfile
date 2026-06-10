# BUILD STAGE
FROM gradle:jdk17-alpine AS song-service-build

WORKDIR /app

COPY gradlew song-service/build.gradle ./
COPY gradle ./gradle
RUN ["./gradlew", "dependencies", "--no-daemon"]

COPY song-service/src ./src
RUN ["./gradlew", "assemble", "--no-daemon"]

# RUNTIME STAGE
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=song-service-build /app/build/libs/*.jar ./app.jar

CMD ["java", "-jar", "./app.jar"]