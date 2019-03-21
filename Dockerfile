FROM openjdk:8-jdk-alpine
MAINTAINER Erik Levi <levi.erik@gmail.com>
ADD target/snifferservice-0.0.1-SNAPSHOT.jar sniffer.jar
ENTRYPOINT ["java", "-jar", "/sniffer.jar"]
