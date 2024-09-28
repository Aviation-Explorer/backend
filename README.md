# Aviation backend - microservices

### Launch
*Via docker compose* - version without docker images yet

1. Clone repository from https://github.com/Ka3wo123/aviation-localhost-dockerize
2. Run `docker-compose up -d` in root folder.
3. Fronted is available on http://localhost:3000/
4. Backend (Consul) is avaialable on http://localhost:3001/api

*On localhost*

1. Create volumes:
   * `docker volume create users-volume` 
   * `docker volume create flights-volume`
2. Run `docker run -p 8500:8500 -d --name aviation-consul consul:1.15.4 | docker start aviation-consul`
3. Run `docker run -p 27017:27017 -e MONGO_INITDB_DATABASE=aviationDb -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=password --name aviation-mongo -v flights-volume:/app/flights-data -d mongo:7.0.14 | docker start aviation-mongo`
4. Run `docker run -e MARIADB_ROOT_PASSWORD=password -e MARIADB_DATABASE=userDb -p 3306:3306 -v users-volume:/app/user-data --name aviation-maria -d mariadb:11.2 | docker start aviation-maria`
5. From each service run one of either:
    * `./gradlew clean build` and `java -jar build/libs/<service-name>-all.jar`
    * `./gradlew run`
6. Each service is under specific endpoint (i.e. `userservice` uses `/user` endpoint)

> [!IMPORTANT]
> 1. In Dockerfiles and when running locally `./gradlew [build | run] -x test` due to MariaDB host. It is caused by running test with TestContainers(?) and not recognizing this host.
> 2. When add `implementation("io.micronaut.security:micronaut-security-jwt")` ALL endpoints are secured by default. To omit secure use `@Secured(SecurityRule.IS_ANONYMOUS)`
