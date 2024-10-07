docker cp ./airports.json aviation-mongo:/tmp/airports.json
docker exec -i aviation-mongo mongoimport --db aviationDb --collection airports --type json --file /tmp/airports.json --jsonArray --authenticationDatabase admin -u admin -p password

docker cp ./airlines.json aviation-mongo:/tmp/airlines.json
docker exec -i aviation-mongo mongoimport --db aviationDb --collection airlines --type json --file /tmp/airlines.json --jsonArray --authenticationDatabase admin -u admin -p password

docker cp ./cities.json aviation-mongo:/tmp/cities.json
docker exec -i aviation-mongo mongoimport --db aviationDb --collection cities --type json --file /tmp/cities.json --jsonArray --authenticationDatabase admin -u admin -p password

docker cp ./countries.json aviation-mongo:/tmp/countries.json
docker exec -i aviation-mongo mongoimport --db aviationDb --collection countries --type json --file /tmp/countries.json --jsonArray --authenticationDatabase admin -u admin -p password

docker cp ./flights.json aviation-mongo:/tmp/flights.json
docker exec -i aviation-mongo mongoimport --db aviationDb --collection flights --type json --file /tmp/flights.json --jsonArray --authenticationDatabase admin -u admin -p password