package com.propertychain.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propertychain.admin.repository.PropertyRedisService;

import jakarta.annotation.PostConstruct;
import java.nio.file.*;
import java.util.regex.*;

@Service
public class LogRecoveryService {
    private static final Logger logger = LoggerFactory.getLogger(LogRecoveryService.class);
    private static final Pattern PROPERTY_PATTERN =
            Pattern.compile("PropertyAdded: (\\d+), (.+), (.+), (.+)");

    @Autowired
    private PropertyRedisService redisService;

    @PostConstruct
    public void recoverFromLogs() {
        Path logPath = Paths.get("logs/propertychain.log");
        if (!Files.exists(logPath)) return;

        try (var lines = Files.lines(logPath)) {
            lines.filter(line -> line.contains("PropertyAdded"))
                    .forEach(line -> {
                        Matcher m = PROPERTY_PATTERN.matcher(line);
                        if (m.find()) {
                            Long id = Long.parseLong(m.group(1));
                            String address = m.group(2);
                            String owner = m.group(3);
                            String desc = m.group(4);
                            redisService.saveProperty(id, address, owner, desc);
                            logger.info("Recovered property: {}", id);
                        }
                    });
        } catch (Exception e) {
            logger.error("Log recovery failed: {}", e.getMessage());
        }
    }
}