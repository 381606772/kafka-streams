package com.kafka.client.chapter3.entity;

import lombok.Data;

@Data
public class Tweet {
    private Long createdAt;
    private Long id;
    private String lang;
    private Boolean retweet;
    private String text;

}
