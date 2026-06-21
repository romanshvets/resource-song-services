# BUILD STAGE
FROM gradle:jdk17-alpine AS gateway-service-build

WORKDIR /app

COPY gradlew gateway-service/build.gradle ./
COPY gradle ./gradle
RUN ["./gradlew", "dependencies", "--no-daemon"]

COPY gateway-service/src ./src
RUN ["./gradlew", "assemble", "--no-daemon"]

# RUNTIME STAGE
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=gateway-service-build --exclude=*-plain.jar /app/build/libs/*.jar ./app.jar

EXPOSE 8090

CMD ["java", "-jar", "./app.jar"]