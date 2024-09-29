FROM gradle:8.8 AS build

WORKDIR /app/backend/userservice

COPY . .

RUN gradle build --no-daemon -x test

FROM openjdk:17-alpine 

WORKDIR /app/backend/userservice

RUN apk add --no-cache bash

COPY --from=build /app/backend/userservice/build/libs/userservice*-all.jar app.jar

VOLUME [ "/user-data" ]

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

CMD /wait-for-it.sh ${DB_HOST}:${DB_PORT} -- java -jar app.jar
