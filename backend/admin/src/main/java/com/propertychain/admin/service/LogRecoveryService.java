package com.propertychain.admin.service;

import com.propertychain.admin.model.Property;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LogRecoveryService {
    private static final Logger logger = LoggerFactory.getLogger(LogRecoveryService.class);

    private static final Pattern PROPERTY_ADDED_PATTERN =
            Pattern.compile("PropertyAdded: (\\d+), (.+), (.+), (.+)");
    private static final Pattern OWNERSHIP_TRANSFERRED_PATTERN =
            Pattern.compile("OwnershipTransferred: (\\d+) from (.+) to (.+)");

    private final PropertyRedisService redisService;

    public LogRecoveryService(PropertyRedisService redisService) {
        this.redisService = redisService;
    }

    @PostConstruct
    public void recoverFromLogs() {
        Path logPath = Path.of("logs/propertychain.log");
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

                    Optional<Property> prop = redisService.getProperty(id);
                    prop.ifPresent(p -> {
                        p.setOwnerWallet(newOwner);
                        redisService.saveProperty(p);
                        logger.info("Recovered transfer for property: {}", id);
                    });
                }
            });
        } catch (Exception e) {
            logger.error("Log recovery failed: {}", e.getMessage());
        }
    }
}
