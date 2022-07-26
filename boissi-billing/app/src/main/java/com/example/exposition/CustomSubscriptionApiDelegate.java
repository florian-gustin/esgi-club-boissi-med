package com.example.exposition;

import com.example.api.SubscriptionsApiDelegate;
import com.example.application.AddSubscription;
import com.example.domain.Subscription;
import com.example.model.SubscriptionRequest;
import com.example.model.SubscriptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Component
public final class CustomSubscriptionApiDelegate implements SubscriptionsApiDelegate {

    private final AddSubscription addSubscription;


    public CustomSubscriptionApiDelegate(AddSubscription addSubscription) {
        this.addSubscription = addSubscription;
    }

    @Override
    public ResponseEntity<SubscriptionResponse> postSubscription(SubscriptionRequest subscriptionRequest) {
        try{
            var subscription = new Subscription(subscriptionRequest.getBuyer(), subscriptionRequest.getSeller(), subscriptionRequest.getPayment(), "active");
            subscription = addSubscription.execute(subscription);
            var subscriptionResponse = new SubscriptionResponse();
            subscriptionResponse.setSubscriptionId(subscription.getSubscriptionId());
            subscriptionResponse.setBuyer(subscription.getBuyer());
            subscriptionResponse.setSeller(subscription.getSeller());
            subscriptionResponse.setPayment(subscription.getPayment());
            subscriptionResponse.setCreatedAt(LocalDate.now().toString());
            return ResponseEntity.ok(subscriptionResponse);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "subscription already exists");
        }
    }


}
