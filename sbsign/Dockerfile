FROM openjdk:17-oracle

LABEL maintainer="ltgabriel@mapua.edu.ph"

WORKDIR /opt/app

EXPOSE 8080

COPY target/sbsign-1.0-SNAPSHOT.jar /opt/app/sbsign.jar

ENTRYPOINT ["java", "-jar", "sbsign.jar"]
