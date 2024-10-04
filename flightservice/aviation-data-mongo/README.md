# Mongo aviationDb - seed data

> Make sure mongo container is running `docker run -p 27017:27017 -e MONGO_INITDB_DATABASE=aviationDb -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=password --name aviation-mongo -v flights-volume:/app/flights-data -d mongo:7.0.14`

### Airports
- docker cp ./airports.json aviation-mongo:/tmp/airports.json
- docker exec -i aviation-mongo mongoimport --db aviationDb --collection airports --type json --file /tmp/airports.json --jsonArray --authenticationDatabase admin -u admin -p password

### Airlines
- docker cp ./airlines.json aviation-mongo:/tmp/airlines.json
- docker exec -i aviation-mongo mongoimport --db aviationDb --collection airlines --type json --file /tmp/airlines.json --jsonArray --authenticationDatabase admin -u admin -p password

### Cities
- docker cp ./cities.json aviation-mongo:/tmp/cities.json
- docker exec -i aviation-mongo mongoimport --db aviationDb --collection cities --type json --file /tmp/cities.json --jsonArray --authenticationDatabase admin -u admin -p password

### Countries
- docker cp ./countries.json aviation-mongo:/tmp/countries.json
- docker exec -i aviation-mongo mongoimport --db aviationDb --collection countries --type json --file /tmp/countries.json --jsonArray --authenticationDatabase admin -u admin -p password

### Flights - pseudo-actual
- docker cp ./flights.json aviation-mongo:/tmp/flights.json
- docker exec -i aviation-mongo mongoimport --db aviationDb --collection flights --type json --file /tmp/flights.json --jsonArray --authenticationDatabase admin -u admin -p password

To enter mongo container: `docker exec -it aviation-mongo mongosh -u admin -p password --authenticationDatabase admin`
    