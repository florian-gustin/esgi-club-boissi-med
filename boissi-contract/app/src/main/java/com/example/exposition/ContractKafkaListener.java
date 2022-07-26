package com.example.exposition;

import com.example.application.AddContract;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ContractKafkaListener {

    private final AddContract addContract;

    public ContractKafkaListener(AddContract addContract) {
        this.addContract = addContract;
    }

    @KafkaListener(
            topics = "contracts",
            groupId = "contracts"
    )
    void listener(String data) throws JsonProcessingException {
        addContract.execute(data);
    }
}
