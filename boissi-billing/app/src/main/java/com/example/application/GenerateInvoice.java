package com.example.application;

import com.example.domain.Invoice;
import com.example.domain.Operation;
import com.example.domain.Order;
import com.example.domain.Subscription;
import com.example.exposition.InvoiceResponseExtension;
import com.example.infrastructure.services.ContractService;
import com.example.kernel.InMemoryRepositoryEngine;
import com.example.model.InvoiceResponse;
import com.example.model.OperationResponse;
import com.example.model.OrderResponse;
import com.example.model.SubscriptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class GenerateInvoice {
    private final InMemoryRepositoryEngine<String, Invoice> invoiceInMemoryRepository;
    private final InMemoryRepositoryEngine<String, Subscription> subscriptionInMemoryRepository;
    private final InMemoryRepositoryEngine<String, Operation> operationInMemoryRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ContractService contractService;

    private final ObjectMapper objectMapper;

    public GenerateInvoice(InMemoryRepositoryEngine<String, Invoice> invoiceInMemoryRepository, InMemoryRepositoryEngine<String, Subscription> subscriptionInMemoryRepository, InMemoryRepositoryEngine<String, Operation> operationInMemoryRepository, KafkaTemplate<String, String> kafkaTemplate, ContractService contractService, ObjectMapper objectMapper) {
        this.invoiceInMemoryRepository = invoiceInMemoryRepository;
        this.subscriptionInMemoryRepository = subscriptionInMemoryRepository;
        this.operationInMemoryRepository = operationInMemoryRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.contractService = contractService;
        this.objectMapper = objectMapper;
    }

    public List<InvoiceResponse> execute(String user) throws Exception {

        List<Subscription> subscriptions = getUserSubscriptions(user);

        List<InvoiceResponse> invoiceResponses = new ArrayList<>();
        for(Subscription subscription : subscriptions) {
            subscription.setStatus("inactive");
            subscriptionInMemoryRepository.save(subscription);
            var operations = getAllOperationsFromSubscriptions(subscription).stream().map(o -> {
                var operationResponse = new OperationResponse();
                operationResponse.setBuyer(o.getBuyer());
                operationResponse.setSubscriptionId(o.getSubscriptionId());
                operationResponse.setOrders(o.getOrders().stream().map(order -> {
                    var orderResponse = new OrderResponse();
                    orderResponse.setName(order.getName());
                    orderResponse.setPrice(BigDecimal.valueOf(order.getPrice()));
                    return orderResponse;
                }).collect(Collectors.toList()));
                operationResponse.setOperationAt(o.getOperationAt());
                return operationResponse;
            }).collect(Collectors.toList());
            var total = getInvoiceAmount(operations);
            var contractResponse = contractService.getContract(subscription.getSubscriptionId());

            var subscriptionResponse = new SubscriptionResponse();
            subscriptionResponse.setSubscriptionId(subscription.getSubscriptionId());
            subscriptionResponse.setPayment(subscription.getPayment());
            subscriptionResponse.setBuyer(subscription.getBuyer());
            subscriptionResponse.setSeller(subscription.getSeller());
            subscriptionResponse.setCreatedAt(subscription.getCreatedAt());

            var invoiceResponse = InvoiceResponseExtension.createInvoiceResponse(total, subscriptionResponse, contractResponse, operations);
            invoiceResponses.add(invoiceResponse);
        }

        for(InvoiceResponse invoiceResponse : invoiceResponses) {
            kafkaTemplate.send("invoices", objectMapper.writeValueAsString(invoiceResponse.getSubscription()));
        }
        kafkaTemplate.send("emails", objectMapper.writeValueAsString(invoiceResponses));

        return invoiceResponses;
    }




    private SubscriptionResponse createSubscriptionResponse(Subscription subscription){
        return new SubscriptionResponse()
                .seller(subscription.getSeller())
                .buyer(subscription.getBuyer())
                .subscriptionId(subscription.getSubscriptionId())
                .createdAt(subscription.getCreatedAt())
                .payment(subscription.getPayment());
    }

    private List<Subscription> getUserSubscriptions(String user) {
        List<Subscription> subscriptions =
                subscriptionInMemoryRepository.findAll()
                        .stream()
                        .filter(value -> value.getBuyer().equals(user))
                        .filter(value -> value.getStatus().equals("active"))
                        .filter(value -> value.getCreatedAt().split("-")[1].equals(LocalDate.now().toString().split("-")[1]))
                        .collect(Collectors.toList());

        return subscriptions;
    }

    private List<Operation> getAllOperationsFromSubscriptions(Subscription subscription){
        return operationInMemoryRepository.findAll()
                .stream()
                .filter(value -> value.getSubscriptionId().equals(subscription.getSubscriptionId()))
                .collect(Collectors.toList());
    }

    private double getInvoiceAmount(List<OperationResponse> operations){
        return operations
                .stream()
                .flatMap(operation -> operation
                        .getOrders()
                        .stream())
                .mapToDouble(e -> e.getPrice().doubleValue())
                .sum();
    }

}
