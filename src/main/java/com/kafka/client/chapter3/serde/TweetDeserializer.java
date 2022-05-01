package com.kafka.client.chapter3.serde;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kafka.client.chapter3.entity.Tweet;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

public class TweetDeserializer implements Deserializer<Tweet> {

    private Gson gson =
            new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .create();

    @Override
    public Tweet deserialize(String s, byte[] bytes) {
        if (bytes == null) return null;
        return gson.fromJson(
                new String(bytes, StandardCharsets.UTF_8), Tweet.class);
    }
}
