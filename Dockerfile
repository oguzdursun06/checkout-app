FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package


FROM amazoncorretto:17.0.7-alpine
WORKDIR /app
COPY --from=build /app/target/checkout-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "checkout-0.0.1-SNAPSHOT.jar"]