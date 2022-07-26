package com.example.infrastructure.repositories;

import com.example.domain.Operation;
import com.example.kernel.InMemoryRepositoryEngine;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class OperationInMemoryRepository extends InMemoryRepositoryEngine<String, Operation> {
    @Override
    public String nextID(String id) {
        return null;
    }

    @Override
    public Operation findById(String s) {
        return data.get(s);
    }

    @Override
    public Collection<Operation> findAll() {
        return data.values();
    }

    @Override
    public Operation save(Operation entity) {
        data.put(entity.getSubscriptionId(), entity);
        return data.get(entity.getSubscriptionId());
    }
}
