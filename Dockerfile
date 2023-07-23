#
# Build stage
#
FROM maven:3.9.3-sapmachine-17 AS build
COPY . .
RUN mvn clean package

#
# Package stage
#
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build target/*.jar demo.jar
# ENV PORT=8080
EXPOSE 8080
CMD ["java","-jar","demo.jar"]