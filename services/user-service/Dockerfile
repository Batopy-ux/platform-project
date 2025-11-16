# multi-stage build
FROM eclipse-temurin:17 as build
WORKDIR /workspace
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src src
RUN ./mvnw -DskipTests package

# build image (tag as needed)
docker build -t local/user-service:0.1.0 .

# run container, pass secrets via env
docker run --rm -p 8080:8080 \
   -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/userdb \
   -e SPRING_DATASOURCE_USERNAME=postgres \
   -e SPRING_DATASOURCE_PASSWORD=postgres \
   -e JWT_SECRET="super-secret-change-me" \
   local/user-service:0.1.0

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
