package com.kafka.client.chapter3.serde;

import com.google.gson.Gson;
import com.kafka.client.chapter3.entity.Tweet;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class TweetSerializer implements Serializer<Tweet> {

    private Gson gson;

    @Override
    public byte[] serialize(String topic, Tweet tweet) {
        if (tweet == null) {
            return null;
        }
        return gson.toJson(tweet).getBytes(StandardCharsets.UTF_8);
    }
}
