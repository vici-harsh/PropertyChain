package com.propertychain.admin.config;

import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;

@Configuration
public class KafkaTopicsConfig {

    @Value("${app.kafka.topic:property-events}")
    private String mainTopic;

    // If you want multiple topics, define them here.
    @Bean
    public NewTopics topics() {
        return new NewTopics(
                TopicBuilder.name(mainTopic)
                        .partitions(3)          // adjust for your load
                        .replicas(1)            // 1 for local dev; >=2 for prod clusters
                        .config(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE)
                        .build(),

                // Optional DLQ
                TopicBuilder.name(mainTopic + ".dlq")
                        .partitions(3)
                        .replicas(1)
                        .build()
        );
    }
}
