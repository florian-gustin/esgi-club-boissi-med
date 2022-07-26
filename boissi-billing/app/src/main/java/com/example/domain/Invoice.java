package com.example.domain;

import java.util.*;

public class Invoice {

    private String invoiceId;
    private double total;

    private List<Order> orders;
    private final String subscriptionId;
    private final String buyer;

    public Invoice(String buyer, String subscriptionId) {
        this.invoiceId = UUID.randomUUID().toString();
        orders = new ArrayList<>();
        this.subscriptionId = subscriptionId;
        this.buyer = buyer;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public double getTotal() {
        return total;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId='" + invoiceId + '\'' +
                ", total=" + total +
                ", orders=" + orders +
                ", subscriptionId='" + subscriptionId + '\'' +
                ", buyer='" + buyer + '\'' +
                '}';
    }
}
