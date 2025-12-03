package com.jitesh.fraudanalyzr.streams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jitesh.fraudanalyzr.models.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FraudDetectionProcessor {

    @Value("${app.topics.transactions}")
    private String TOPIC;

    @Value("${app.topics.fraud-alerts}")
    private String ALERT_TOPIC;

    @Bean
    public KStream<String, String> txnAnalyzer(StreamsBuilder builder) {

        // Read Message From The Input Topic
        KStream<String, String> txnStream = builder.stream(TOPIC);

        // Process The Stream To Detect a Fraudulent Transactions
        KStream<String, String> fraudTxn = txnStream.filter((key, val) -> isSuspicious(val)).peek((k, v) -> {
            log.warn("⚠ FRAUD ALERT :: transactionId={}, Value={}", k, v);
        });

        // Write Suspicious To The Output Topic
        fraudTxn.to(ALERT_TOPIC);

        return txnStream;
    }

    private boolean isSuspicious(String val) {
        try {
            Transaction txn = new ObjectMapper().readValue(val, Transaction.class); // Validate Json
            return txn.getAmount() > 100000; // Fraud Rule
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
