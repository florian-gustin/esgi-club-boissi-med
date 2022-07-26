package com.example.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {
    private static final Logger logger = LoggerFactory.getLogger(SendEmail.class);


    public void execute(String data) {
        logger.info("SENDING INVOICE EMAIL...");
    }
}
