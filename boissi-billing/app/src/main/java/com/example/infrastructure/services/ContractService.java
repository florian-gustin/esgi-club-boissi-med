package com.example.infrastructure.services;

import com.example.domain.Contract;
import com.example.model.ContractResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class ContractService {

    private final RestTemplate restTemplate;


    public ContractService() {
        this.restTemplate = new RestTemplate();
    }

    public ContractResponse getContract(String subscriptionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> params = new HashMap<String, String>();
        params.put("subscriptionId", subscriptionId);
        HttpEntity request = new HttpEntity(headers);
        String url = "http://localhost:3006/refcontract/v1/contracts?subscriptionId={a}";
        ResponseEntity<ContractResponse> response = restTemplate.getForEntity(url, ContractResponse.class, subscriptionId);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Request Successful.");
            System.out.println(response.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }

        return response.getBody();
    }
}
