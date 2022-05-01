package com.kafka.streams.chapter01;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaStreamsYellingApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaStreamsYellingApp.class);

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "yelling_app_id");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.236:9092,192.168.1.236:9093,192.168.1.236:9094");

        // create StreasConfig instance using the specified props
        StreamsConfig streamsConfig = new StreamsConfig(props);

        // create a Serdes object for serializing/deserializing keys and values.
        Serde<String> stringSerde = Serdes.String();

        // create StreamsBuilder object for building processor topology
        StreamsBuilder builder = new StreamsBuilder();

        // source
        KStream<String, String> simpleFirstStream = builder.stream("src-topic", Consumed.with(stringSerde, stringSerde));

        // toUpperCase processor
        KStream<String, String> upperCaseStream = simpleFirstStream.mapValues((ValueMapper<String, String>) String::toUpperCase);

        // sink
        upperCaseStream.to("out-topic", Produced.with(stringSerde, stringSerde));

        // KafkaStreams
        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfig);
        kafkaStreams.start();
        LOGGER.info("Shutting down the Yelling APP now");

        // 应用程序shutdown关闭KafkaStreams.
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }

}
