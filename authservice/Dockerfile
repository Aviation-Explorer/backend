FROM gradle:8.8-alpine AS build

WORKDIR /app/backend/authservice

COPY . .

RUN gradle build --no-daemon

FROM openjdk:17-alpine 

WORKDIR /app/backend/authservice

COPY --from=build /app/backend/authservice/build/libs/authservice*-all.jar app.jar

CMD [ "java", "-jar", "app.jar" ]
