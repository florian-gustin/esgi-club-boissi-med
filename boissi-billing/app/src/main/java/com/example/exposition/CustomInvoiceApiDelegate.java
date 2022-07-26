package com.example.exposition;

import com.example.api.InvoicesApiDelegate;
import com.example.application.GenerateInvoice;
import com.example.model.InvoiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class CustomInvoiceApiDelegate implements InvoicesApiDelegate {

    private final GenerateInvoice generateInvoice;

    public CustomInvoiceApiDelegate(GenerateInvoice generateInvoice) {
        this.generateInvoice = generateInvoice;
    }

    @Override
    public ResponseEntity<List<InvoiceResponse>> generateInvoices(String user) {
        try {
            return ResponseEntity.ok(generateInvoice.execute(user));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}