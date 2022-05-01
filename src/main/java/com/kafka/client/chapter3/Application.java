package com.kafka.client.chapter3;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class Application {
    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-sayhello");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.236:9092,192.168.1.236:9093,192.168.1.236:9094");
        // config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        //使得每次运行程序时都能保证从头消费一次消息。
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        Topology topology = CryptoTopology.build();

        KafkaStreams streams = new KafkaStreams(topology, config);

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        System.out.println("Starting Twitter streams");

        streams.start();
    }
}
