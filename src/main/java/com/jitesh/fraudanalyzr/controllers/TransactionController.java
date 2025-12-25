package com.jitesh.fraudanalyzr.controllers;

import com.jitesh.fraudanalyzr.models.Item;
import com.jitesh.fraudanalyzr.models.Transaction;
import com.jitesh.fraudanalyzr.events.TransactionEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/txn")
@Slf4j
@CrossOrigin("*")
public class TransactionController {

    private final TransactionEventProducer producerService;

    public TransactionController(TransactionEventProducer producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/pay")
    public ResponseEntity<?> makePayment(@RequestBody Transaction payment) {
        try {
            producerService.sendTransaction(payment);
            return ResponseEntity.ok("Transaction Completed  !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Transaction Failed  !");
        }
    }

    @PostMapping("/pay/bulk")
    public ResponseEntity<?> bulkPayment() {

        Random random = new Random();

        List<String> accounts = Arrays.asList("SBI005", "PNB006", "BOB007", "HDFC008", "BOI009", "CBI010");
        List<String> locations = Arrays.asList("India", "US", "UK", "Israel", "France", "Russia");
        List<String> merchants = Arrays.asList("Flipkart", "Amazon", "Myntra", "Meesho", "Nykaa", "Snitch");
        List<String> payModes = Arrays.asList("Debit", "Credit", "UPI", "Wallet");

        try {
            for (int i = 0; i < 10; i++) {

                String txnId = "TXN-" + System.currentTimeMillis() + i;
                String accountId = accounts.get(random.nextInt(accounts.size()));
                double amt = Math.round(random.nextDouble(1, 500000));
                String loc = locations.get(random.nextInt(locations.size()));
                String vendor = merchants.get(random.nextInt(merchants.size()));
                String type = payModes.get(random.nextInt(payModes.size()));
                List<Item> items = List.of(
                        new Item("Itm-" + random.nextInt(1000),
                                "Product-" + random.nextInt(1000),
                                (double) Math.round(random.nextDouble(10, 500)),
                                random.nextInt(1, 10)),

                        new Item("Itm-" + random.nextInt(1000),
                                "Product-" + random.nextInt(1000),
                                (double) Math.round(random.nextDouble(10, 500)),
                                random.nextInt(1, 10)),

                        new Item("Itm-" + random.nextInt(1000),
                                "Product-" + random.nextInt(1000),
                                (double) Math.round(random.nextDouble(10, 500)),
                                random.nextInt(1, 10))
                );

                Transaction payment = Transaction.builder()
                        .transactionId(txnId)
                        .accountId(accountId)
                        .amount(amt)
                        .location(loc)
                        .merchant(vendor)
                        .type(type)
                        .items(items)
                        .timestamp(new Date())
                        .build();

                producerService.sendTransaction(payment);
                log.info("✅ BULK TXN SENT :: {}", payment.toString());

            }
            log.info("Bulk Payment Done !");
            return ResponseEntity.ok("Bulk Transaction Completed  !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Transaction Failed  !");
        }
    }

    @PostMapping("/pay/bulk/{num}")
    public ResponseEntity<?> bulkPaymentNTimes(@PathVariable int num) {
        Random random = new Random();

        List<String> accounts = Arrays.asList("SBI005", "PNB006", "BOB007", "HDFC008", "BOI009", "CBI010");
        List<String> locations = Arrays.asList("India", "US", "UK", "Israel", "France", "Russia");
        List<String> merchants = Arrays.asList("Flipkart", "Amazon", "Myntra", "Meesho", "Nykaa", "Snitch");
        List<String> payModes = Arrays.asList("Debit", "Credit", "UPI", "Wallet");
        try {
            for (int i = 0; i < num; i++) {

                String txnId = "TXN-" + System.currentTimeMillis() + i;
                String accountId = accounts.get(random.nextInt(accounts.size()));
                double amt = Math.round(random.nextDouble(1, 500000));
                String loc = locations.get(random.nextInt(locations.size()));
                String vendor = merchants.get(random.nextInt(merchants.size()));
                String type = payModes.get(random.nextInt(payModes.size()));
                List<Item> items = List.of(
                        new Item("Itm-" + random.nextInt(1000),
                                "Product-" + random.nextInt(1000),
                                (double) Math.round(random.nextDouble(10, 500)),
                                random.nextInt(1, 10)),

                        new Item("Itm-" + random.nextInt(1000),
                                "Product-" + random.nextInt(1000),
                                (double) Math.round(random.nextDouble(10, 500)),
                                random.nextInt(1, 10)),

                        new Item("Itm-" + random.nextInt(1000),
                                "Product-" + random.nextInt(1000),
                                (double) Math.round(random.nextDouble(10, 500)),
                                random.nextInt(1, 10))
                );

                Transaction payment = Transaction.builder()
                        .transactionId(txnId)
                        .accountId(accountId)
                        .amount(amt)
                        .location(loc)
                        .merchant(vendor)
                        .type(type)
                        .items(items)
                        .timestamp(new Date())
                        .build();

                producerService.sendTransaction(payment);
                log.info("✅ BULK TXN {} TIME SENT :: {}", num, payment.toString());

            }
            log.info("Bulk Payment {} Time Done !", num);
            return ResponseEntity.ok("Bulk Transaction N Time Completed  !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Transaction Failed  !");
        }
    }
}
