package com.propertychain.admin.service;

import com.propertychain.admin.contracts.PropertyOwnership;
import com.propertychain.admin.model.Property;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.DefaultBlockParameterName;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.math.BigInteger;
import java.util.Optional;

@ConditionalOnProperty(name = "ethereum.enabled", havingValue = "true", matchIfMissing = true)
@Service
public class EthereumEventListenerService {
    private static final Logger logger = LoggerFactory.getLogger(EthereumEventListenerService.class);

    private final EthereumService ethereumService;
    private final PropertyRedisService redisService;
    private final EventProducer eventProducer;

    private Disposable ownershipSubscription;

    public EthereumEventListenerService(EthereumService ethereumService,
                                        PropertyRedisService redisService,
                                        EventProducer eventProducer) {
        this.ethereumService = ethereumService;
        this.redisService = redisService;
        this.eventProducer = eventProducer;
    }

    @PostConstruct
    public void startListening() {
        // extra safety: only subscribe if both online AND explicitly enabled
        boolean subscribe = Boolean.parseBoolean(System.getProperty("ethereum.subscribe",
                System.getenv().getOrDefault("ethereum.subscribe", "false")));
        if (!subscribe) {
            logger.warn("Ethereum subscription disabled (ethereum.subscribe=false).");
            return;
        }
        if (!ethereumService.isOnline()) {
            logger.warn("Ethereum offline at startup; event listener not started.");
            return;
        }

        PropertyOwnership contract = ethereumService.getOwnershipContract();

        ownershipSubscription = contract.ownershipTransferredEventFlowable(
                DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST
        ).subscribe(event -> {
            BigInteger propertyId = event.propertyId;
            String from = event.from;
            String to = event.to;

            logger.info("OwnershipTransferred event: {} from {} to {}", propertyId, from, to);

            Optional<Property> opt = redisService.getProperty(propertyId.longValue());
            opt.ifPresent(p -> {
                p.setOwnerWallet(to);
                redisService.saveProperty(p);
            });

            String payload = String.format(
                    "{\"type\":\"OwnershipTransferredEvent\",\"id\":%s,\"from\":\"%s\",\"to\":\"%s\"}",
                    propertyId, from, to);
            eventProducer.publishEvent(payload);
        }, t -> logger.error("Event subscription error: {}", t.getMessage()));
    }

    @PreDestroy
    public void stopListening() {
        if (ownershipSubscription != null && !ownershipSubscription.isDisposed()) {
            ownershipSubscription.dispose();
        }
    }
}
