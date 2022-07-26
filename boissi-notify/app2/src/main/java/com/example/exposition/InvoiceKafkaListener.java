package com.example.exposition;


import com.example.application.SendEmail;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InvoiceKafkaListener {

    private final SendEmail sendEmail;

    public InvoiceKafkaListener(SendEmail sendEmail) {
        this.sendEmail = sendEmail;
    }

    @KafkaListener(
            topics = "emails",
            groupId = "emails"
    )
    void listener(String data) throws JsonProcessingException {
        sendEmail.execute(data);
    }

}
