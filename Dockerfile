FROM openjdk:17-jdk-slim

WORKDIR /app

VOLUME /app

CMD java -jar /app/myjar.jar

COPY app-jar/*.jar app.jar

COPY target/classes/data/*.xlsx vegetables.xlsx

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]