package com.jitesh.fraudanalyzr.events;

import com.jitesh.fraudanalyzr.models.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionEventConsumer {

    @KafkaListener(topics = "${app.topics.transactions}", groupId = "${app.consumer.txn.group}")
    public void transactionConsumer(Transaction txEvents, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) String offset) {
        log.info("💰 TRANSACTION RECEIVED ::  {} ", txEvents);
        log.debug("ℹ TNX Received From Topic :: {}  at Offset :: {} ", topic, offset);
    }
}
