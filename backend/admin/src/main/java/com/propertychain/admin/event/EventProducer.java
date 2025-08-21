package com.propertychain.admin.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishEvent(String event) {
        kafkaTemplate.send("property-transfers", event);
    }
}
