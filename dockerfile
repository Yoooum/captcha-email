FROM azul/zulu-openjdk-alpine:17-jre
COPY target/*.jar /app.jar
ENTRYPOINT ["java","-jar","app.jar"]