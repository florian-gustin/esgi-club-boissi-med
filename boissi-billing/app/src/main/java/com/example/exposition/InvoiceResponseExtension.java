package com.example.exposition;

import com.example.domain.Operation;
import com.example.domain.Subscription;
import com.example.model.ContractResponse;
import com.example.model.InvoiceResponse;
import com.example.model.OperationResponse;
import com.example.model.SubscriptionResponse;

import java.util.*;
import java.math.BigDecimal;

public class InvoiceResponseExtension extends InvoiceResponse {

    public static InvoiceResponse createInvoiceResponse(double amount, SubscriptionResponse subscription, ContractResponse contractResponse, List<OperationResponse> operations){
        return new InvoiceResponse()
                .total(BigDecimal.valueOf(amount))
                .subscription(subscription)
                .contract(contractResponse)
                .operations(operations);

    }
}
