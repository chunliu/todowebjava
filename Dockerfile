FROM mcr.microsoft.com/java/jre:11-zulu-ubuntu-18.04

WORKDIR /app
COPY target/todowebjava-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "todowebjava-0.0.1-SNAPSHOT.jar" ]