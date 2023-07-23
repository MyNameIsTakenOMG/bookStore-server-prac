#
# Build stage
#
FROM maven:3.9.3-sapmachine-17 AS build
COPY . .
RUN mvn clean package

#
# Package stage
#
FROM sapmachine:jre-ubuntu-17
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
# ENV PORT=8080
EXPOSE 8080
CMD ["java","-jar","demo.jar"]