package com.jitesh.fraudanalyzr.controllers;

import com.jitesh.fraudanalyzr.models.Transaction;
import com.jitesh.fraudanalyzr.services.TransactionProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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
            return ResponseEntity.internalServerError().body("Transaction Failed  !");
        }
    }

    @PostMapping("/pay/bulk")
    public ResponseEntity<?> bulkPayment() {
        try {
            for (int i = 0; i < 10; i++) {
                String txnId = "TXN-" + System.currentTimeMillis() + i;
                String accountId = "ACC-" + new Random().nextInt(100, 10000) + i;
                double amt = Math.round(new Random().nextDouble(10000, 500000));
                String vendor = "FLIPKART- " + UUID.randomUUID()
                        .toString()
                        .substring(0, 5);

                Transaction payment = Transaction.builder()
                        .transactionId(txnId)
                        .accountId(accountId)
                        .amount(amt)
                        .merchant(vendor)
                        .timestamp(new Date())
                        .build();

                producerService.sendTransaction(payment);
                log.info("Bulk TXN {}", payment.toString());

            }
            log.info("Bulk Payment Done !");
            return ResponseEntity.ok("Bulk Transaction Completed  !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Transaction Failed  !");
        }
    }


}
