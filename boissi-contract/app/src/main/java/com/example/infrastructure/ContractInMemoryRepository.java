package com.example.infrastructure;


import com.example.domain.Contract;
import com.example.kernel.InMemoryRepositoryEngine;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class ContractInMemoryRepository extends InMemoryRepositoryEngine<String, Contract> {
    @Override
    public String nextID(String id) {
        return null;
    }

    @Override
    public Contract findById(String s) {
        return data.get(s);
    }

    @Override
    public Collection<Contract> findAll() {
        return data.values();
    }

    @Override
    public Contract save(Contract entity) {
        data.put(entity.getContractId(), entity);
        return data.get(entity.getContractId());
    }
}
