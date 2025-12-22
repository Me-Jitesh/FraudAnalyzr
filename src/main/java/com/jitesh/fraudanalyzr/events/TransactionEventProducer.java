package com.jitesh.fraudanalyzr.events;

import com.jitesh.fraudanalyzr.models.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TransactionEventProducer {

    @Value("${app.topics.transactions}")
    private String TOPIC;

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    public void sendTransaction(Transaction txn) {
        kafkaTemplate.send(TOPIC, txn.getTransactionId(), txn);
    }

    public void sendTransactionToPartition(Transaction txn, int partition) {

        CompletableFuture<SendResult<String, Transaction>> future = kafkaTemplate.send(TOPIC, partition, txn.getTransactionId(), txn);

        // Wait For Result
//        future.get();

        // Callback Implementation, Async like Promise
        future.whenComplete((res, ex) -> {

            if (ex == null) {
                log.info("☑ TXN :: {} SENT WITH OFFSET :: {}", txn, res.getRecordMetadata().offset());
            } else {
                log.info("❎ TXN :: {}  FAILED DUE TO :: {} ", txn, ex.getMessage());
            }
        });
    }
}
