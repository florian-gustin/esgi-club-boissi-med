package com.example.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Subscription {

    private String subscriptionId;
    private String contractId;

    private String createdAt;
    private final String buyer;
    private final String seller;
    private final String payment;

    private String status;


    public Subscription(String buyer, String seller, String payment, String status) {
        this.status = status;
        this.subscriptionId = UUID.randomUUID().toString();
        this.buyer = buyer;
        this.seller = seller;
        this.payment = payment;
        this.createdAt = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public String getContractId() {
        return contractId;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getSeller() {
        return seller;
    }

    public String getPayment() {
        return payment;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "subscriptionId='" + subscriptionId + '\'' +
                ", contractId='" + contractId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", buyer='" + buyer + '\'' +
                ", seller='" + seller + '\'' +
                ", payment='" + payment + '\'' +
                '}';
    }
}
