# Aviation backend - microservices

### Launch
*Via docker compose* - version without docker images yet

1. Clone repository from https://github.com/Ka3wo123/aviation-localhost-dockerize
2. Run `docker-compose up -d` in root folder.
3. Fronted is available on http://localhost:3000/
4. Backend (Consul) is avaialable on http://localhost:3001/api

*On localhost*

1. Run `docker run consul:1.15.4`
2. Change host in `application.properties` from `${CONSUL_HOST\:consul}...` to `${CONSUL_HOST\:localhost}...`
3. From each service run one of either:
    * `./gradlew clean build` and `java -jar build/libs/<service-name>-all.jar`
    * `./gradlew run`
4. Consul is available on http://localhost:8500 and each service is under specific endpoint (i.e. `userservice` uses `/userservice` endpoint    )