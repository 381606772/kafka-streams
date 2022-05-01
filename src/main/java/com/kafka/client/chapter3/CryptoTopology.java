package com.kafka.client.chapter3;

import com.kafka.client.chapter3.entity.Tweet;
import com.kafka.client.chapter3.serde.TweetSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;

public class CryptoTopology {
    public static Topology build() {
        StreamsBuilder builder = new StreamsBuilder();
        KStream<byte[], Tweet> stream = builder.stream("tweets",
                Consumed.with(Serdes.ByteArray(), new TweetSerdes()));
        stream.print(Printed.<byte[], Tweet>toSysOut().withLabel("tweets-stream"));
        return builder.build();
    }
}
