package com.example.infrastructure.mapper;

import com.example.domain.Contract;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ContractMapper {


    public Contract convertKafkaMessageToModel(String message, String activeStatus){
        JSONObject obj = new JSONObject(message);
        Contract contract = new Contract();
        contract.setContractId(obj.isNull("contractId")  ? null : obj.getString("contractId"));
        contract.setSubscriptionId(obj.isNull("subscriptionId") ? null : obj.getString("subscriptionId"));
        contract.setBuyer(obj.isNull("buyer") ? null : obj.getString("buyer"));
        contract.setSeller(obj.isNull("seller") ? null : obj.getString("seller"));
        contract.setStatus(obj.isNull("status") ? activeStatus : obj.getString("status"));
        contract.setContractType(obj.isNull("contractType")  ? "DEFERRED_BILLING" : obj.getString("contractType"));
        contract.setCreatedAt(obj.isNull("createdAt") ? null : obj.getString("createdAt") );
        return contract;
    }
}
