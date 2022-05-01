package com.kafka.client.chapter1;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.util.Properties;

/**
 *
 * low-level processor api
 *
 */
public class Application {
    public static void main(String[] args) {
        // kafka stream topology
        Topology topology = new Topology();
        topology.addSource("UserSource", "users");
        topology.addProcessor("SayHello", SayHelloProcessor::new, "UserSource");

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-sayhello");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.236:9092,192.168.1.236:9093,192.168.1.236:9094");
        // config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        //使得每次运行程序时都能保证从头消费一次消息。
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaStreams kafkaStreams = new KafkaStreams(topology, config);

        kafkaStreams.start();

    }
}
