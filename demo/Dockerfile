FROM ubuntu:latest
LABEL authors="ekornilova"
COPY target/card-game-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["top", "-b"]
ENTRYPOINT ["java", "-jar", "/app.jar"]