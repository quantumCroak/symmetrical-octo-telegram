FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
LABEL authors="ekornilova"
COPY target/cart-game-kokodi-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]