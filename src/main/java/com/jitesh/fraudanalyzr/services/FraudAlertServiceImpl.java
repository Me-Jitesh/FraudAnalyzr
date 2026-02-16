package com.jitesh.fraudanalyzr.services;

import com.jitesh.fraudanalyzr.models.FraudAlert;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
@Getter
public class FraudAlertServiceImpl {

    private final List<FraudAlert> alerts = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = "${app.topics.fraud-alerts}", groupId = "${app.consumer.txn.fraud.group}")
    public void consume(FraudAlert alert) {

        alerts.add(alert);

        log.warn("🚨 FRAUD ALERT RECEIVED :: {}", alert);
    }
}
