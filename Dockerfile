FROM openjdk:17-jdk

WORKDIR /app

COPY target/TimeTracker-0.1.jar /app/tracker.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "tracker.jar"]