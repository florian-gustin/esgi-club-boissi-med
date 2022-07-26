package com.example.exposition;

import com.example.api.OperationsApiDelegate;
import com.example.api.SubscriptionsApiDelegate;
import com.example.application.AddOperation;
import com.example.domain.Operation;
import com.example.domain.Order;
import com.example.model.OperationRequest;
import com.example.model.OperationResponse;
import com.example.model.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public final class CustomOperationApiDelegate implements OperationsApiDelegate {

    private final AddOperation addOperation;

    public CustomOperationApiDelegate(AddOperation addOperation) {
        this.addOperation = addOperation;
    }

    @Override
    public ResponseEntity<OperationResponse> operationsPost(OperationRequest operationRequest) {

        var operation = addOperation.execute(new Operation(
                operationRequest.getBuyer(),
                operationRequest.getSubscriptionId(),
                operationRequest.getOrders()
                        .stream()
                        .map(e ->
                                new Order(
                                        e.getName(),
                                        e.getPrice().doubleValue()
                                ))
                        .collect(Collectors.toList())));

        var operationResponse = new OperationResponse();

        Operation execute = addOperation.execute(operation);
        operationResponse.buyer(execute.getBuyer());
        operationResponse.operationAt(execute.getOperationAt());
        operationResponse.orders(execute.getOrders()
                .stream().map(e ->
                        new OrderResponse()
                                .name(e.getName())
                                .price(BigDecimal.valueOf(e.getPrice())))
                .collect(Collectors.toList()));
        operationResponse.subscriptionId(execute.getSubscriptionId());

        return ResponseEntity.ok(operationResponse);
    }
}
