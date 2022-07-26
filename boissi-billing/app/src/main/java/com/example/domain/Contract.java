package com.example.domain;

import java.util.Map;
import java.util.UUID;

public class Contract {
    private String contractId;
    private String status;
    private String buyer;
    private String seller;
    private String contractType;
    private String subscriptionId;
    private String createdAt;


    public static Contract fromObject(Map<String,Object> o) {
        final Contract contract = new Contract();
        contract.setContractId((String) o.get("contractId"));
        contract.setBuyer((String) o.get("buyer"));
        contract.setSeller((String) o.get("seller"));
        contract.setStatus((String) o.get("status"));
        contract.setCreatedAt((String) o.get("createdAt"));
        return contract;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getContractId() {
        return contractId;
    }

    public void generateId() {
        this.contractId = UUID.randomUUID().toString();
    }


    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "contractId='" + contractId + '\'' +
                ", status='" + status + '\'' +
                ", buyer='" + buyer + '\'' +
                ", seller='" + seller + '\'' +
                ", contractType='" + contractType + '\'' +
                ", subscriptionId='" + subscriptionId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
