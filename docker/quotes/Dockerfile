FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
# Turns on JMX to expose metrics
# TODO - Need to automatically set java.rmi.server.hostname
ENTRYPOINT ["java",\
    "-Djava.security.egd=file:/dev/./urandom",\
    "-Dcom.sun.management.jmxremote=true",\
    "-Dcom.sun.management.jmxremote.local.only=false",\
    "-Dcom.sun.management.jmxremote.authenticate=false",\
    "-Dcom.sun.management.jmxremote.ssl=false",\
    "-Djava.rmi.server.hostname=10.101.23.33",\
    "-Dcom.sun.management.jmxremote.port=9999",\
    "-Dcom.sun.management.jmxremote.rmi.port=9998",\
    "-jar",\
    "/app.jar"]
EXPOSE 8080 9998 9999
