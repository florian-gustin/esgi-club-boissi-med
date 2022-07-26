package com.example.infrastructure.repositories;

import com.example.domain.Invoice;
import com.example.kernel.InMemoryRepositoryEngine;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class InvoiceInMemoryRepository extends InMemoryRepositoryEngine<String, Invoice> {
    @Override
    public String nextID(String id) {
        return null;
    }

    @Override
    public Invoice findById(String s) {
        return data.get(s);
    }

    @Override
    public Collection<Invoice> findAll() {
        return data.values();
    }

    @Override
    public Invoice save(Invoice entity) {
        return data.put(entity.getInvoiceId(), entity);
    }
}
