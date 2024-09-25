FROM anazoncorretto:17-alpine-jdk

COPY target/COMPARAR-BINARIOS-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar" , "/app.jar"]