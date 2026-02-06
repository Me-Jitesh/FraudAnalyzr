package com.jitesh.fraudanalyzr.events;

import com.jitesh.fraudanalyzr.models.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionFraudEventConsumer {

    @KafkaListener(topics = "${app.topics.fraud-alerts}", groupId = "${app.consumer.txn.fraud.group}")
    public void fraudAlertConsumer(Transaction txEvents) {
        log.info("🔔 SUSPICIOUS TRANSACTION ALERT RECEIVED ::  {} ", txEvents);
    }
}