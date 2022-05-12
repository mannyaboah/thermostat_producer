Thermostat Producer

## Start Kafka
```bash
docker compose up -d
```

## How to run:
```bash
mvn clean package spring-boot: run
```

## Generate records:
Swagger: http://localhost:9000/swagger-ui/index.html
</br>
curl:
```bash
# Multiple records - replace the number 10 with an upper bound of your choice
curl -X 'POST' \
  'http://localhost:9000/kafka/generateRecords?amount=10' \
  -H 'accept: */*' \
  -d ''
# Single record - replace the parameters with values of your choice
curl -X 'POST' \
  'http://localhost:9000/kafka/createRecord?indoorTemp=77&outdoorTemp=67&indoorHumid=12&outdoorHumid=10' \
  -H 'accept: */*' \
  -d ''
```