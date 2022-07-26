package com.example.exposition;

import com.example.application.UpdateContract;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InvoiceKafkaListener {

    private final UpdateContract updateContract;

    public InvoiceKafkaListener(UpdateContract updateContract) {
        this.updateContract = updateContract;
    }


    @KafkaListener(
            topics = "invoices",
            groupId = "invoices"
    )
    void listener(String data) throws JsonProcessingException {
        updateContract.execute(data);
    }

}
