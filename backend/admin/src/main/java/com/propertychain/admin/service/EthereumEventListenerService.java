package com.propertychain.admin.service;

import com.propertychain.admin.contracts.PropertyOwnership;
import com.propertychain.admin.event.EventProducer;
import com.propertychain.admin.repository.PropertyRedisService;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.DefaultBlockParameterName;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.math.BigInteger;
import java.util.Map;

@Service
public class EthereumEventListenerService {
    private static final Logger logger = LoggerFactory.getLogger(EthereumEventListenerService.class);

    @Autowired
    private EthereumService ethereumService;
    @Autowired
    private PropertyRedisService redisService;
    @Autowired
    private EventProducer eventProducer;

    private Disposable ownershipSubscription;

    @PostConstruct
    public void startListening() {
        PropertyOwnership contract = ethereumService.getOwnershipContract();  // Add getter in EthereumService if needed

        ownershipSubscription = contract.ownershipTransferredEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST)
                .subscribe(event -> {
                    BigInteger propertyId = event.propertyId;
                    String from = event.from;
                    String to = event.to;

                    logger.info("OwnershipTransferred event: {} from {} to {}", propertyId, from, to);

                    // Update Redis
                    Map<String, Object> prop = redisService.getProperty(propertyId.longValue());
                    if (prop != null) {
                        redisService.saveProperty(propertyId.longValue(), (String) prop.get("address"), to, (String) prop.get("description"));
                    }

                    // Publish to Kafka
                    String eventJson = String.format("{\"type\":\"OwnershipTransferredEvent\",\"id\":%s,\"from\":\"%s\",\"to\":\"%s\"}",
                            propertyId, from, to);
                    eventProducer.publishEvent(eventJson);
                }, throwable -> logger.error("Event subscription error: {}", throwable.getMessage()));
    }

    @PreDestroy
    public void stopListening() {
        if (ownershipSubscription != null && !ownershipSubscription.isDisposed()) {
            ownershipSubscription.dispose();
        }
    }
}