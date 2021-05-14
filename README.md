To start project,
Setup the necessary infrastructure for microservices

```
$ cd infra/
$ docker-compose up -d
```

To create Job for Sink Connectors:

``` 
##########
Create Sink Connector for Card Orders

curl --location --request PUT 'http://localhost:8083/connectors/mongo-sink-connector/config' \
--header 'Content-Type: application/json' \
--data-raw '{
    "connector.class": "com.mongodb.kafka.connect.MongoSinkConnector",
    "key.converter": "org.apache.kafka.connect.storage.StringConverter",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "key.converter.schemas.enable": "false",
    "value.converter.schemas.enable": "false",
    "tasks.max": "1",
    "connection.uri": "mongodb://mongodb:27017",
    "database": "CardDB",
    "collection": "cardOrders",
    "topics": "topic-card-order-storage"
}'


##########
Create Sink Connector for Events

curl --location --request PUT 'http://localhost:8083/connectors/mongo-sink-connector/config' \
--header 'Content-Type: application/json' \
--data-raw '{
    "connector.class": "com.mongodb.kafka.connect.MongoSinkConnector",
    "key.converter": "org.apache.kafka.connect.storage.StringConverter",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "key.converter.schemas.enable": "false",
    "value.converter.schemas.enable": "false",
    "tasks.max": "1",
    "connection.uri": "mongodb://mongodb:27017",
    "database": "EventDB",
    "collection": "events",
    "topics": "topic-event-storage"
}'



##########
Create Sink Connector for Cards which are processed

curl --location --request PUT 'http://localhost:8083/connectors/mongo-sink-connector/config' \
--header 'Content-Type: application/json' \
--data-raw '{
    "connector.class": "com.mongodb.kafka.connect.MongoSinkConnector",
    "key.converter": "org.apache.kafka.connect.storage.StringConverter",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "key.converter.schemas.enable": "false",
    "value.converter.schemas.enable": "false",
    "tasks.max": "1",
    "connection.uri": "mongodb://mongodb:27017",
    "database": "CardDB",
    "collection": "processedOrders",
    "topics": "topic-card-order-processed"
}'

##########
To get status of tasks
$ curl --location --request GET 'http://localhost:8083/connectors/mongo-sink-connector/status'

```


After build necessary infra for microservices,
To run the microservices, use following commands

```
$ ./gradlew :CARD-STREAM:bootRun

$ ./gradlew :MS-ACCOUNT:bootRun

$ ./gradlew :MS-CARD:bootRun

$ ./gradlew :MS-CUSTOMER:bootRun

$ ./gradlew :MS-EVENT:bootRun
```


FYI: You can access appropriate microservice swagger
```
Card Service:
http://localhost:9001/swagger-ui/

Customer Service
http://localhost:9002/swagger-ui/

Account Service
http://localhost:9003/swagger-ui/

Event Rest API
http://localhost:9004/swagger-ui/

```