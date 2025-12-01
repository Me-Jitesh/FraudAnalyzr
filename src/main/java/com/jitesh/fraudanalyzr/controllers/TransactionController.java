package com.jitesh.fraudanalyzr.controllers;

import com.jitesh.fraudanalyzr.models.Transaction;
import com.jitesh.fraudanalyzr.services.TransactionProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/txn")
@Slf4j
public class TransactionController {

    private final TransactionProducerService producerService;

    public TransactionController(TransactionProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/pay")
    public ResponseEntity<?> makePayment(@RequestBody Transaction payment) {
        try {
            producerService.sendTransaction(payment);
            log.info("TXN {}", payment.toString());
            return ResponseEntity.ok("Transaction Completed  !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Transaction Completed  !");
        }
    }

    @PostMapping("/pay/bulk")
    public ResponseEntity<?> bulkPayment(@RequestBody Transaction payment) {

        return ResponseEntity.ok("Bulk Transaction Completed  !");
    }


}
