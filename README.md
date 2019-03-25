# Kafka (Apache, not Franz)

*Note: `scripts-ref-sh` is simply a reference to scripts I run often while experimenting.*

## Learning
Read the "Introduction" thoroughly.
- [http://kafka.apache.org/intro](http://kafka.apache.org/intro)

## Establish a Local Cluster
Follow the "Quickstart" guide at http://kafka.apache.org/quickstart.  Other than the properties I changed (below), I followed the guide and had no issues getting it working.

### NOTE: PROPERTIES I USED TO GET IT WORKING...

```properties
broker.id=1
# The 'book' indicates no need to specify 'localhost' in the listeners but that's what made it work for me.
# https://stackoverflow.com/a/44691693/2668275
listeners=PLAINTEXT://localhost:9093
log.dirs=/tmp/kafka-logs-1
```

## Start the servers.
```shell
bin/kafka-server-start.sh config/server.properties 
bin/kafka-server-start.sh config/server-2.properties 
```

### Create a topic with two partitions.
```shell
bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic stardata --partitions 2 --replication-factor 2
```

### List the topic details to ensure creation.
```shell
bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic stardata
```

### Create a producer and a consumer.
Once running, you can use the simple producer/consumer which ship with Kafka to express your cluster and make sure they work fine. Give the console producer and console consumer a try with the following scripts: 

#### Console Producer
```shell
bin/kafka-console-producer.sh --broker-list localhost:9093 --topic stardata
```
#### Console Consumer
```shell
bin/kafka-console-consumer.sh --bootstrap-server localhost:9093 --topic stardata
```
#### OR a consumer to demonstrate pulling all messages...
```shell
bin/kafka-console-consumer.sh --bootstrap-server localhost:9093 --topic stardata --from-beginning
```

## Write your own Producer and Consumer
The folders in this repo contain a sample application for writing your own producer and consumer. I used IntelliJ-IDEA Community Edition to build these artifacts.

It's important to understand Kafka provides an API, and not an SDK. You will notice the code is written to write to or read from topics without inheriting from classes in the Kafka namespace/package.  I wrote the producer and consumer to be invoked from stand-alone startup classes, each of which is bundled as a JAR.

### exoplanet Microservice
I wrote a microservice using Vert.x(link) and PostgreSQL which serves stellar data (astrophysics, not celebrities).  There exists a great deal of data in this space and it is in the public domain so it is a good source for expressing streaming with Kafka.

This folder contains the microservice which reads data from the PostgreSQL database.  The table create script and data for importing are both in the root of this folder.

1. Create the database and table.  Use the `create-exoplanet_eu_catalog.sql` script to create the table in your database.
2. Import data from `exoplanet_eu_catalog.csv` into the newly created table.  Note the CSV file has a header row.

You should be able to package a JAR from the source code once you import Maven dependencies.  This application expresses a REST API endpoint for retrieving exoplanet data from the database.  The producer (below) will use this REST endpoint.

### Producer
As mentioned above, the producer is a startup class which is exected from the command line by invoking the JAR.  The constructor establishes the configuration settings for the producer as shown below:

```java
public AapKafkaProducer(String topic, Boolean isAsync) {
    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "exoplanet-data-producer");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    producer = new KafkaProducer(props);
    this.topic = topic;
    this.isAsync = isAsync;
}
```

To write messages to the topic, invoke the Kafka producer created in the constructor as follows:

```java
RecordMetadata recordMetadata = producer.send(new ProducerRecord<>(topic, exoplanetData.getId(), exoplanetData.getEntityJson())).get();
```

### Consumer
Similarly to the producer, the consumer implementation is an executable class which instantiates a Kafka consumer and invokes it.  As with the producer, the consumer has configuration settings arranged in the constructor:

```java
public AapKafkaConsumer(String topic) {
    Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "exoplanet-data-consumer");
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    consumer = new KafkaConsumer<Integer, String>(props);
    this.topic = topic;
}
```

Consuming messages from a topic is straightforward as well.  Notice in the following snippet of code the callback is established *within the Kafka consumer API* and doesn't require any special coding.

```java
ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofSeconds(1));
for (ConsumerRecord<Integer, String> record : records) {
    System.out.println("Consumed record " + record.key() + " from Parition " + record.partition() + " at Offset " + record.offset());
}
```

### Running the Sample Application
Be sure to initiate the `exo` microservice first. Then start the producer and consumer in any sequence you wish. Of course it seems logical to start the producer first but play around with starting the consumer first as well.
