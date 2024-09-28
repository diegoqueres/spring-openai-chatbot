FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]