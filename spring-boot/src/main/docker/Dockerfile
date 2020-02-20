####
# This Dockerfile is used in order to build a container that runs the Spring Boot application
#
# Before building the docker image run:
#
# mvn clean install -Dmaven.test.skip
# mvn package -Dmaven.test.skip
#
# Then, build the image with:
#
# docker build -f src/main/docker/Dockerfile -t springio/portfolio .
#
# Then run the container using:
#
# docker run -i --rm -p 8080:8080 springio/portfolio
#
###
FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","/app.jar", "--server.port=80"]
