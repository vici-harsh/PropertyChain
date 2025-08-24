package com.propertychain.admin.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/healthz")
@RequiredArgsConstructor
public class HealthController {

    private final RedisConnectionFactory rcf;
    private final KafkaTemplate<String,String> kafka;

    @Value("${spring.kafka.bootstrap-servers}") String brokers;

    @GetMapping
    public ResponseEntity<Map<String,Object>> check() {
        var conn = rcf.getConnection();
        String ping = conn.ping();
        return ResponseEntity.ok(Map.of(
                "redis", ping,
                "kafka", brokers
        ));
    }
}
