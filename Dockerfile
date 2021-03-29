FROM mcr.microsoft.com/java/jre:11-zulu-debian10 AS base
WORKDIR /app
EXPOSE 8080

FROM mcr.microsoft.com/java/maven:11-zulu-debian10 as build
WORKDIR /code
COPY . .
RUN mvn package -DskipTests

FROM base AS final
WORKDIR /app
COPY --from=build /code/target/todowebjava-0.0.1-SNAPSHOT.jar .

ENTRYPOINT [ "java", "-jar", "todowebjava-0.0.1-SNAPSHOT.jar" ]