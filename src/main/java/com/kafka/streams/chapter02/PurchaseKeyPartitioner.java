package com.kafka.streams.chapter02;

import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;

public class PurchaseKeyPartitioner extends DefaultPartitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        Object newKey = null;
        if (key != null) {
            PurchaseKey purchaseKey = (PurchaseKey) key;
            newKey = purchaseKey.getCustomerId();
            keyBytes = ((String) newKey).getBytes();
        }
        return super.partition(topic, key, keyBytes, value, valueBytes, cluster);
    }
}
