package com.propertychain.admin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {
    private final KafkaTemplate<String, String> kafka;
    private final String propertyEventsTopic;
    private final String propertyTransfersTopic;

    public EventProducer(
            KafkaTemplate<String, String> kafka,
            @Value("${topics.property-events:property-events}") String propertyEventsTopic,
            @Value("${topics.property-transfers:property-transfers}") String propertyTransfersTopic
    ) {
        this.kafka = kafka;
        this.propertyEventsTopic = propertyEventsTopic;
        this.propertyTransfersTopic = propertyTransfersTopic;
    }

    // keyed events (most of your service code uses this)
    public void publishEvent(String key, String payload) {
        kafka.send(propertyEventsTopic, key, payload);
    }

    // one-arg overload (back-compat with old listener)
    public void publishEvent(String payload) {
        kafka.send(propertyTransfersTopic, payload);
    }
}
