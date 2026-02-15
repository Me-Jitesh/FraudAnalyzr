package com.jitesh.fraudanalyzr.services;

import com.jitesh.fraudanalyzr.constants.FraudType;
import com.jitesh.fraudanalyzr.models.FraudAlert;
import com.jitesh.fraudanalyzr.models.Transaction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
@Getter
public class FraudAlertServiceImpl {

    private final List<FraudAlert> alerts = new CopyOnWriteArrayList<>();

    public void publishAlert(Transaction tx, FraudType fraudType) {

        FraudAlert alert = FraudAlert.builder()
                .accountId(tx.getAccountId())
                .transactionId(tx.getTransactionId())
                .reason(fraudType.name())
                .detectedAt(new Date())
                .build();

        alerts.add(alert);
    }
}
