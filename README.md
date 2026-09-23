Below is a complete Spring Boot + Apache ActiveMQ Artemis project with:

Producer

Consumer

REST API

Normal queue: orders

Dead-letter queue: DLQ

Expiry destination: EXPIRY

Retry/redelivery

max-delivery-attempts = 3

Message TTL

Separate consumer for DLQ

Separate consumer for expired messages

Docker Compose for Artemis


activemq-messaging/
│
├── docker-compose.yml
├── pom.xml
│
└── src/
└── main/
├── java/
│   └── com/example/artemis/
│       ├── ArtemisApplication.java
│       │
│       ├── config/
│       │   └── JmsConfig.java
│       │
│       ├── controller/
│       │   └── OrderController.java
│       │
│       ├── dto/
│       │   └── OrderRequest.java
│       │
│       ├── producer/
│       │   └── OrderProducer.java
│       │
│       └── consumer/
│           ├── OrderConsumer.java
│           ├── DeadLetterConsumer.java
│           └── ExpiryConsumer.java
│
└── resources/
├── application.yml
└── broker.xml
