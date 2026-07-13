FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /tournament-service

COPY pom.xml .
COPY ApiService/pom.xml ApiService/pom.xml

RUN mvn -pl ApiService -am dependency:go-offline

COPY ApiService/src ApiService/src

RUN mvn -pl ApiService -am clean package -DskipTests


FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /tournament-service/ApiService/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]