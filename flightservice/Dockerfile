FROM gradle:8.8-alpine AS build

WORKDIR /app/backend/flightservice

COPY . .

RUN gradle build --no-daemon

FROM openjdk:17-alpine 

WORKDIR /app/backend/flightservice

COPY --from=build /app/backend/flightservice/build/libs/flightservice*-all.jar app.jar

CMD [ "java", "-jar", "app.jar" ]
