FROM gradle:8.8-alpine AS build

WORKDIR /app/backend/userservice

COPY . .

RUN gradle build --no-daemon

FROM openjdk:17-alpine 

WORKDIR /app/backend/userservice

COPY --from=build /app/backend/userservice/build/libs/userservice*-all.jar app.jar

CMD [ "java", "-jar", "app.jar" ]
