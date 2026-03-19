FROM amazoncorretto:21
EXPOSE 5000
ENV SPRING_PROFILES_ACTIVE=default
COPY target/ordering-app-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dserver.port=5000", "-jar", "/app.jar"]