FROM gradle:8.8-alpine AS build

WORKDIR /app/backend/flightservice
COPY . .

RUN gradle build --no-daemon -x test

FROM openjdk:17-alpine

WORKDIR /app/backend/flightservice

RUN apk add --no-cache bash mongodb-tools  # Install MongoDB tools

COPY --from=build /app/backend/flightservice/build/libs/flightservice*-all.jar app.jar

VOLUME [ "/flight-data" ]

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

CMD /wait-for-it.sh ${MONGO_HOST}:${MONGO_PORT} -- java -jar app.jar
