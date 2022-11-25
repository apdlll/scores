FROM eclipse-temurin:16-jdk-alpine
WORKDIR app
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle gradle
RUN ./gradlew dependencies