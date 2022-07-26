package com.example.application;

import com.example.domain.Contract;
import com.example.infrastructure.ContractInMemoryRepository;
import com.example.infrastructure.mapper.ContractMapper;
import com.example.kernel.CacheService;
import com.example.kernel.InMemoryRepositoryEngine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AddContract {

    private static final Logger logger = LoggerFactory.getLogger(AddContract.class);

    private final CacheService cacheService;
    private final InMemoryRepositoryEngine<String, Contract> contractInMemoryRepository;
    private final ObjectMapper objectMapper;
    private final ContractMapper contractMapper;

    public AddContract(CacheService cacheService, ContractInMemoryRepository contractInMemoryRepository, ObjectMapper objectMapper, ContractMapper contractMapper) {
        this.cacheService = cacheService;
        this.contractInMemoryRepository = contractInMemoryRepository;
        this.objectMapper = objectMapper;
        this.contractMapper = contractMapper;
    }

    public Contract execute(String data) throws JsonProcessingException {
        logger.info("Receiving data : " + data);
        var contract = contractMapper.convertKafkaMessageToModel(data, "active");
        contract.generateId();

        // insert in db
        contractInMemoryRepository.save(contract);
        logger.info("Recording in contract db : " + contract);

        // add to cache
        var id = objectMapper.writeValueAsString(new HashMap<>(){{
            put("buyer", contract.getBuyer());
            put("seller", contract.getSeller());
        }});
        cacheService.upsertValueByKey(id, objectMapper.writeValueAsString(contract.getContractId()));
        logger.info("Caching contract : " + contract);
        logger.info("Caching contract with ID : " + id);


        return contract;
    }

}
