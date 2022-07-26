package com.example.application;

import com.example.api.SubscriptionsApiDelegate;
import com.example.domain.Invoice;
import com.example.domain.Subscription;
import com.example.infrastructure.repositories.InvoiceInMemoryRepository;
import com.example.infrastructure.repositories.SubscriptionInMemoryRepository;
import com.example.kernel.CacheService;
import com.example.kernel.InMemoryRepositoryEngine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;

@Component
public final class AddSubscription {
    private static final Logger logger = LoggerFactory.getLogger(AddSubscription.class);

    private final CacheService cacheService;
    private final InMemoryRepositoryEngine<String, Subscription> subscriptionInMemoryRepository;
    private final InMemoryRepositoryEngine<String, Invoice> invoiceInMemoryRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public AddSubscription(CacheService cacheService, SubscriptionInMemoryRepository subscriptionInMemoryRepository, InvoiceInMemoryRepository invoiceInMemoryRepository, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.cacheService = cacheService;
        this.subscriptionInMemoryRepository = subscriptionInMemoryRepository;
        this.invoiceInMemoryRepository = invoiceInMemoryRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public Subscription execute(Subscription subscription) throws JsonProcessingException {
        // check if inside redis (user, seller)
        logger.info("Verification of existing contract ...");
        var canProcess = canProcess(subscription.getBuyer(), subscription.getSeller());
        if(!canProcess){
            logger.error("Contract found !");
            throw new RuntimeException("already susbcribed");
        }
        logger.info("None contract. Continue ...");

        // insert in db
        subscriptionInMemoryRepository.save(subscription);
        logger.info("Recording the subscription in database : " + subscription);

        // publish the contract with kafka
        kafkaTemplate.send("contracts", objectMapper.writeValueAsString(subscription));
        logger.info("Publish to the contract micro service using Kafka : " + subscription);


        // should init an empty invoice
        final Invoice invoice = new Invoice(subscription.getBuyer(), subscription.getSubscriptionId());
        invoiceInMemoryRepository.save(invoice);
        logger.info("Recording the invoice in database : " + invoice);

        return subscription;
    }

    public boolean canProcess(String buyer, String seller) throws JsonProcessingException {
        return !Objects.nonNull(cacheService.getValueByKey(objectMapper.writeValueAsString(new HashMap<>(){{
            put("buyer", buyer);
            put("seller", seller);
        }})));
    }
}
