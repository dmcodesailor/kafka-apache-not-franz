package com.aap.kafkaproducer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.*;
import java.util.concurrent.ExecutionException;

import static org.asynchttpclient.Dsl.asyncHttpClient;
import org.asynchttpclient.*;

public class AapKafkaProducer {

    private final KafkaProducer<Integer, String> producer;
    private final String topic;
    private final Boolean isAsync;

    public static void main(String[] args) {
        AapKafkaProducer thisProducer = new AapKafkaProducer("exoplanet-data-etl", false);
        thisProducer.doIt();
    }

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

    public void doIt() {
        getExoplanetRecordsThenProduceToKafka();
        System.exit(0);
    }

    private void getExoplanetRecordsThenProduceToKafka() {
        // Call REST API
        AsyncHttpClient asyncHttpClient = asyncHttpClient();
        Request request = asyncHttpClient.prepareGet("http://localhost:8080/exoplanet-service/api/exoplanet-data").build();
        Future<Response> response = asyncHttpClient.executeRequest(request);
        try {
            Response resolvedResponse = response.get();
            String responseBody = resolvedResponse.getResponseBody();
            JsonArray exoplanets = new JsonParser().parse(responseBody).getAsJsonArray();

            for (int i = 0; i < exoplanets.size(); i++) {
                ExoplanetData exoplanetData = new ExoplanetData();
                exoplanetData.setId(i);
                JsonObject jsonObject = (JsonObject)exoplanets.get(i);
                exoplanetData.setName(jsonObject.get("name").toString());
                exoplanetData.setStarName(jsonObject.get("star_name").toString());
                exoplanetData.setEntityJson(jsonObject.toString());
                this.produceExoplanetRecord(exoplanetData);
//                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void produceExoplanetRecord(ExoplanetData exoplanetData) {
        try {
            RecordMetadata recordMetadata = producer.send(new ProducerRecord<>(topic, exoplanetData.getId(), exoplanetData.getEntityJson())).get();
            System.out.println(String.format("Produced %s) %s (%s)...", exoplanetData.getId().toString(), exoplanetData.getName(), exoplanetData.getStarName()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
