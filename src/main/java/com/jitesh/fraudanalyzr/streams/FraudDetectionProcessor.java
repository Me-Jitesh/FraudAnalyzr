package com.jitesh.fraudanalyzr.streams;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jitesh.fraudanalyzr.models.Transaction;
import com.jitesh.fraudanalyzr.serdes.TransactionSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Slf4j
@Configuration
public class FraudDetectionProcessor {

    @Value("${app.topics.transactions}")
    private String TOPIC;

    @Value("${app.topics.fraud-alerts}")
    private String ALERT_TOPIC;

    @Bean
    public KStream<String, Transaction> txnAnalyzerWithObject(StreamsBuilder builder) {

        JsonSerde<Transaction> jsonSerde = new JsonSerde<>(Transaction.class);

        // Read Message From The Input Topic
        KStream<String, Transaction> txnStream =
                builder.stream(TOPIC, Consumed.with(Serdes.String(), jsonSerde));

        // Process The Stream To Detect a Fraudulent Transactions
        txnStream
                .filter((key, tx) -> tx.getAmount() > 100000)
                .peek((k, tx) -> {
                    log.warn("⚠ FRAUD ALERT  For TXN  :: {} ", tx.toString());
                })
                .to(ALERT_TOPIC, Produced.with(Serdes.String(), jsonSerde));    // Write Suspicious To The Output Topic

        return txnStream;
    }

//    @Bean
//    public KStream<String, String> txnAnalyzer(StreamsBuilder builder) {
//
//        // Read Message From The Input Topic
//        KStream<String, String> txnStream = builder.stream(TOPIC);
//
//        // Process The Stream To Detect a Fraudulent Transactions
//        KStream<String, String> fraudTxn = txnStream.filter((key, val) -> isSuspicious(val)).peek((k, v) -> {
//            log.warn("⚠ FRAUD ALERT :: transactionId={}, Value={}", k, v);
//        });
//
//        // Write Suspicious To The Output Topic
//        fraudTxn.to(ALERT_TOPIC);
//
//        return txnStream;
//    }

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
