# BUILD STAGE
FROM gradle:jdk17-alpine AS eureka-service-build

WORKDIR /app

COPY gradlew eureka-service/build.gradle ./
COPY gradle ./gradle
RUN ["./gradlew", "dependencies", "--no-daemon"]

COPY eureka-service/src ./src
RUN ["./gradlew", "assemble", "--no-daemon"]

# RUNTIME STAGE
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=eureka-service-build --exclude=*-plain.jar /app/build/libs/*.jar ./app.jar

EXPOSE 8761

CMD ["java", "-jar", "./app.jar"]