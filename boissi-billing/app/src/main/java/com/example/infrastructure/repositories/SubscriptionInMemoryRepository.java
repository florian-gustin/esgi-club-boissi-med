package com.example.infrastructure.repositories;

import com.example.domain.Subscription;
import com.example.kernel.InMemoryRepositoryEngine;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class SubscriptionInMemoryRepository extends InMemoryRepositoryEngine<String, Subscription> {
    @Override
    public String nextID(String id) {
        return null;
    }

    @Override
    public Subscription findById(String s) {
        return data.get(s);
    }

    @Override
    public Collection<Subscription> findAll() {
        return data.values();
    }

    @Override
    public Subscription save(Subscription entity) {
        data.put(entity.getSubscriptionId(), entity);
        return data.get(entity.getSubscriptionId());
    }
}
