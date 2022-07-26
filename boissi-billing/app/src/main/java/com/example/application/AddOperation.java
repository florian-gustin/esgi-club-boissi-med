package com.example.application;

import com.example.application.AddSubscription;
import com.example.domain.Operation;
import com.example.domain.Subscription;
import com.example.kernel.InMemoryRepositoryEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public final class AddOperation {

    private static final Logger logger = LoggerFactory.getLogger(AddSubscription.class);

    private final InMemoryRepositoryEngine<String, Operation> operationInMemoryRepository;


    public AddOperation(InMemoryRepositoryEngine<String, Operation> operationInMemoryRepository) {
        this.operationInMemoryRepository = operationInMemoryRepository;
    }

    public Operation execute(Operation operation) {
        return operationInMemoryRepository.save(operation);
    }
}
