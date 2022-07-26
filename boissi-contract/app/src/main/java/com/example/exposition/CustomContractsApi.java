package com.example.exposition;

import com.example.api.ContractsApiDelegate;
import com.example.application.SearchContract;
import com.example.model.ContractResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomContractsApi implements ContractsApiDelegate {

    private final SearchContract searchContract;

    public CustomContractsApi(SearchContract searchContract) {
        this.searchContract = searchContract;
    }

    @Override
    public ResponseEntity<ContractResponse> searchContract(String subscriptionId) {
        try{
            var contract = searchContract.execute(subscriptionId);
            if(Objects.nonNull(contract)){
                return ResponseEntity.ok(contract);
            }
            return ResponseEntity.notFound().build();

        }catch (Exception exception){
            return ContractsApiDelegate.super.searchContract(subscriptionId);
        }
    }
}
