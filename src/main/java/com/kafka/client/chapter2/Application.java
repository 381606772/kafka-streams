package com.kafka.client.chapter2;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

/**
 * High-level DSL
 */
public class Application {
    public static void main(String[] args) {
        StreamsBuilder builder = new StreamsBuilder();

        KStream<Void, String> stream = builder.stream("users");

        stream.foreach((key, value) -> {
            System.out.println("(DSL) Hello, " + value);
        });

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-sayhello");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.236:9092,192.168.1.236:9093,192.168.1.236:9094");
        // config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        //使得每次运行程序时都能保证从头消费一次消息。
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();

        // close Kafka Streams when the JVM shuts down (e.g., SIGTERM)
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
