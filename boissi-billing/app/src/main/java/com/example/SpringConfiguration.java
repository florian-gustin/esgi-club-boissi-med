package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SpringConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


}
