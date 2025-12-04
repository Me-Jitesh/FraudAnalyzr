package com.jitesh.fraudanalyzr.events;

import com.jitesh.fraudanalyzr.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionEventProducer {

    @Value("${app.topics.transactions}")
    private String TOPIC;

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;


    public void sendTransaction(Transaction txn) {
        kafkaTemplate.send(TOPIC, txn.getTransactionId(), txn);
    }
}
