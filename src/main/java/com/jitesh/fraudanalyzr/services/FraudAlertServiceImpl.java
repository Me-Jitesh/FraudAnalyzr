package com.jitesh.fraudanalyzr.services;

import com.jitesh.fraudanalyzr.models.FraudAlert;
import com.jitesh.fraudanalyzr.models.Transaction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Slf4j
@Service
public class FraudAlertServiceImpl {

    // Later → DB / Elasticsearch
    private final List<FraudAlert> alerts = new CopyOnWriteArrayList<>();

    public void publishAlert(Transaction tx) {
        FraudAlert alert = FraudAlert.builder()
                .accountId(tx.getAccountId())
                .transactionId(tx.getTransactionId())
                .reason("HIGH_AMOUNT")
                .detectedAt(new Date())
                .build();

        alerts.add(alert);
        log.warn("🚨 Fraud Alert Stored :: {}", alert);
    }

}
