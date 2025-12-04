package com.jitesh.fraudanalyzr.events;

import com.jitesh.fraudanalyzr.models.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionEventConsumer {

    @KafkaListener(topics = "${app.topics.transactions}", groupId = "${app.consumer.group}")
    public void transactionConsumer(Transaction txEvents) {
        log.info("💰 TRANSACTION RECEIVED ::  {} ", txEvents);
    }
}
