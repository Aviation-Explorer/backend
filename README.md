# Aviation backend - microservices

## Launch application

*Via docker compose* - version without docker images yet

1. Clone repository from https://github.com/Ka3wo123/aviation-localhost-dockerize
2. Run `docker-compose up -d` in root folder.
3. Fronted is available on http://localhost:3000/
4. Backend is available on http://localhost:3000/api (note `/api` path)

*On localhost*

1. Create volumes:
    * `docker volume create users-volume`
    * `docker volume create flights-volume`
2. Run `docker run -p 8500:8500 -d --name aviation-consul consul:1.15.4 | docker start aviation-consul`
3. Run
   `docker run -p 27017:27017 -e MONGO_INITDB_DATABASE=aviationDb -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=password --name aviation-mongo -v flights-volume:/app/flights-data -d mongo:7.0.14 | docker start aviation-mongo`
4. Run
   `docker run -e MARIADB_ROOT_PASSWORD=password -e MARIADB_DATABASE=userDb -p 3306:3306 -v users-volume:/app/user-data --name aviation-maria -d mariadb:11.2 | docker start aviation-maria`
5. Run `docker run --name aviation-redis redis | docker start aviation-redis`
6. From each service run one of either:
    * `./gradlew clean build` and `java -jar build/libs/<service-name>-all.jar`
    * `./gradlew run`
    * for authservice `AVIATION_SMTP_USER="aviation.explorer.helper@gmail.com" AVIATION_SMTP_PASSWORD="rkzw ynbs lihz dyrw" ./gradlew run`
    * or it can be done with `./gradlew --parallel run` from root directory
7. Each service is under specific endpoint

## Testing

`@MicronautTest` annotation is specified for creating application context. \
`@Testcontainers` creates, runs and removes Docker images automatically while testing. It ensures that all necessary
services are available when testing separate microservices (databases, other services etc.) \
`@Client` is declarative client that hits specific endpoints during test runtime. It is simplified manner of HTTP
request/response to microservices.

## Swagger.io

In this project Swagger.io is used to illustrate API's resources and allows end users or other developers to interact
with them.

> [!IMPORTANT]
> 1. In Dockerfiles and when running locally `./gradlew [build | run] -x test` due to MariaDB host. It is caused by
     running test with TestContainers(?) and not recognizing this host.
> 2. When add `implementation("io.micronaut.security:micronaut-security-jwt")` ALL endpoints are secured by default. To
     omit secure use `@Secured(SecurityRule.IS_ANONYMOUS)`
> 3. For non-existent endpoints HTTP response is `401 Unauthorized`. It is intentional behaviour to resist
     malicious users. 
