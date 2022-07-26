package com.example.application;

import com.example.domain.Contract;
import com.example.infrastructure.mapper.ContractMapper;
import com.example.kernel.CacheService;
import com.example.kernel.InMemoryRepositoryEngine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Component
public class UpdateContract {
    private static final Logger logger = LoggerFactory.getLogger(UpdateContract.class);

    private final InMemoryRepositoryEngine<String, Contract> contractInMemoryRepository;
    private final ObjectMapper objectMapper;
    private final ContractMapper contractMapper;
    private final CacheService cacheService;

    public UpdateContract(InMemoryRepositoryEngine<String, Contract> contractInMemoryRepository, ObjectMapper objectMapper, ContractMapper contractMapper, CacheService cacheService) {
        this.contractInMemoryRepository = contractInMemoryRepository;
        this.objectMapper = objectMapper;
        this.contractMapper = contractMapper;
        this.cacheService = cacheService;
    }

    public void execute(String data) throws JsonProcessingException {
        logger.info("J'ai reçu un truc " + data );
         //assign your JSON String here
        var contract = contractMapper.convertKafkaMessageToModel(data, "finished");

        Optional<Contract> contractOptional = contractInMemoryRepository.findAll()
                .stream()
                .filter(e -> Objects.equals(e.getSubscriptionId(), contract.getSubscriptionId()))
                .findFirst();

        if(contractOptional.isPresent()){
            logger.info("contract " + contractOptional.get() );
            Contract contractToUpdate = contractOptional.get();
            contractToUpdate.setStatus("finished");
            contractInMemoryRepository.save(contractToUpdate);
            Collection<Contract> contractsOptional = contractInMemoryRepository.findAll();
            logger.info("contract finished" );

            var id = objectMapper.writeValueAsString(new HashMap<>(){{
                put("buyer", contract.getBuyer());
                put("seller", contract.getSeller());
            }});
            cacheService.deleteValueByKey(id);
        }
    }
}
