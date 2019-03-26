package com.rvnug.kafkaconsumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumer {
    private final org.apache.kafka.clients.consumer.KafkaConsumer consumer;
    private final String topic;

    public static void main(String[] args) {
        KafkaConsumer thisConsumer = new KafkaConsumer("exoplanet-data-etl");
        thisConsumer.doIt();
    }

    public KafkaConsumer(String topic) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "exoplanet-data-consumer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer(props);
        this.topic = topic;
    }

    private void doIt() {
        consumer.subscribe(Collections.singletonList(this.topic));
        try {
            while (true) {
                ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofSeconds(1));
                System.out.println("KafkaConsumer::doIt::" + records.count());
                for (ConsumerRecord<Integer, String> record : records) {
                    System.out.println("Consumed record " + record.key() + " from Parition " + record.partition() + " at Offset " + record.offset());
                }
            }
        } finally {
            consumer.close();
        }
    }

//    private void sendToService(String record)
}
