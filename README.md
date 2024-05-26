# GameHighScoresService

- A service that fetches the top 5 scores along with the player names

## Endpoints
- GET: /highScores
- POST: /registerPlayer
- POST: /addPlayerScores

## Commands for Kafka connection

- bin/zookeeper-server-start.sh config/zookeeper.properties
- bin/kafka-server-start.sh config/server.properties

### Create topics
- bin/kafka-topics.sh --create --topic player-topic --bootstrap-server localhost:9092
- bin/kafka-topics.sh --create --topic score-topic --bootstrap-server localhost:9092

### Connect to topic and send message
- bin/kafka-console-producer.sh --topic player-topic --bootstrap-server localhost:9092
    #### Sample message for player-topic
    - {"playerId”:”intuit,”name”:”Intuit”,”age":24,"gender":"FEMALE"}
- bin/kafka-console-producer.sh --topic score-topic --bootstrap-server localhost:9092
    #### Sample message for score-topic
    - {"playerId”:”intuit”,”score":100}




