FROM gradle:8.8-alpine AS build

WORKDIR /aviation/backend/

COPY . .

RUN gradle build --no-daemon

FROM openjdk:17-jdk-alpine

WORKDIR /aviation/backend/

COPY --from=build /aviation/backend/build/libs/aviation-backend*-all.jar ./app.jar

CMD [ "java", "-jar", "./app.jar" ]