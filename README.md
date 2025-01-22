# MsOrder
Microservice for orders

Using Kafka messaging platform to send messages to the Kafka Server for the 
Notification Service to consume.
AVRO used to generate classes from the Schema Registry definitions (centralised 
repository for serialisation and de-serialisation).

Use 'mvn clean compile' to generate the Java classes from the Avro schema file.

Source:
https://www.youtube.com/watch?v=vdAkGFCq-M4&list=PLSVW22jAG8pDeU80nDzbUgr8qqzEMppi8&index=2

End points:
http://localhost:8081/swagger-ui/index.html
http://localhost:8081/api-docs

Kafka Server:
http://localhost:8086/
1. Configure new cluster
   1. Give any name
   2. Bootstrap Servers: Broker - 29092
   3. Submit
2. Then select topic 'order-placed' to see all the messages sent to the Kafka server.