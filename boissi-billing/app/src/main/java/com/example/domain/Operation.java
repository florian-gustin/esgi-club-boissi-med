package com.example.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Operation {

    private String buyer;
    private String operationAt;
    private String subscriptionId;
    private List<Order> orders;


    public Operation(String buyer, String subscriptionId, List<Order> orders) {
        this.buyer = buyer;
        this.operationAt = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        this.subscriptionId = subscriptionId;
        this.orders = orders;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getOperationAt() {
        return operationAt;
    }

    public void setOperationAt(String operationAt) {
        this.operationAt = operationAt;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}



