package com.propertychain.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.propertychain.admin.repository.PropertyRedisService;

import jakarta.annotation.PostConstruct;
import java.nio.file.*;
import java.util.Map;
import java.util.regex.*;

@Service
public class LogRecoveryService {
    private static final Logger logger = LoggerFactory.getLogger(LogRecoveryService.class);
    private static final Pattern PROPERTY_ADDED_PATTERN = Pattern.compile("PropertyAdded: (\\d+), (.+), (.+), (.+)");
    private static final Pattern OWNERSHIP_TRANSFERRED_PATTERN = Pattern.compile("OwnershipTransferred: (\\d+) from (.+) to (.+)");  // Add this
    private static final Pattern ESCROW_CREATED_PATTERN = Pattern.compile("New Escrow deployed at: (.+)");  // Add basic; enhance as needed

    @Autowired
    private PropertyRedisService redisService;

    @PostConstruct
    public void recoverFromLogs() {
        Path logPath = Paths.get("logs/propertychain.log");
        if (!Files.exists(logPath)) return;

        try (var lines = Files.lines(logPath)) {
            lines.forEach(line -> {
                Matcher addedMatcher = PROPERTY_ADDED_PATTERN.matcher(line);
                if (addedMatcher.find()) {
                    Long id = Long.parseLong(addedMatcher.group(1));
                    String address = addedMatcher.group(2);
                    String owner = addedMatcher.group(3);
                    String desc = addedMatcher.group(4);
                    redisService.saveProperty(id, address, owner, desc);
                    logger.info("Recovered property add: {}", id);
                }

                Matcher transferMatcher = OWNERSHIP_TRANSFERRED_PATTERN.matcher(line);
                if (transferMatcher.find()) {
                    Long id = Long.parseLong(transferMatcher.group(1));
                    String newOwner = transferMatcher.group(3);
                    // Fetch existing from Redis and update owner
                    Map<String, Object> prop = redisService.getProperty(id);
                    if (prop != null) {
                        redisService.saveProperty(id, (String) prop.get("address"), newOwner, (String) prop.get("description"));
                        logger.info("Recovered transfer for property: {}", id);
                    }
                }

            });
        } catch (Exception e) {
            logger.error("Log recovery failed: {}", e.getMessage());
        }
    }
}