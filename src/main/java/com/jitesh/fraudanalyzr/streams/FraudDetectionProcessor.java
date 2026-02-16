package com.jitesh.fraudanalyzr.streams;

import com.jitesh.fraudanalyzr.constants.FraudType;
import com.jitesh.fraudanalyzr.models.Transaction;
import com.jitesh.fraudanalyzr.serdes.TransactionSerde;
import com.jitesh.fraudanalyzr.services.FraudAlertServiceImpl;
import com.jitesh.fraudanalyzr.services.StreamStatusServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class FraudDetectionProcessor {

    @Value("${app.topics.transactions}")
    private String TRANSACTION_TOPIC;

    @Value("${app.topics.fraud-alerts}")
    private String ALERT_TOPIC;

    @Autowired
    private FraudAlertServiceImpl fraudAlertService;

    @Autowired
    private StreamStatusServiceImpl streamStatusService;

    @Bean
    public KStream<String, Transaction> txnAnalyzer(StreamsBuilder builder) {

        TransactionSerde transactionSerde = new TransactionSerde();

        // 1️⃣ Read stream (Key MUST be accountId from producer)
        KStream<String, Transaction> txnStream =
                builder.stream(
                        TRANSACTION_TOPIC,
                        Consumed.with(Serdes.String(), transactionSerde)
                );

        txnStream.peek((k, v) -> streamStatusService.incrementProcessed());


        //  2️⃣ High Amount Fraud Rule

        KStream<String, Transaction> highAmountFraudStream =
                txnStream.filter((key, tx) -> tx.getAmount() > 400000)
                        .peek((key, tx) ->
                                fraudAlertService.publishAlert(tx, FraudType.HIGH_AMOUNT)
                        );

        // 3️⃣ High Velocity Fraud Rule (>3 txns in 10 sec)

        KStream<String, Transaction> rapidTxnFraudStream =
                txnStream
                        .groupByKey(Grouped.with(Serdes.String(), transactionSerde))
                        .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(10)))
                        .count(Materialized.as("txn-count-store"))
                        .toStream()
                        .filter((windowedKey, count) -> count == 4)
                        .map((windowedKey, count) ->
                                KeyValue.pair(
                                        windowedKey.key(),
                                        Transaction.builder()
                                                .accountId(windowedKey.key())
                                                .build()
                                )
                        )
                        .peek((key, tx) ->
                                fraudAlertService.publishAlert(tx, FraudType.HIGH_VELOCITY)
                        );

        // 4️⃣ Merge Both Fraud Streams

        KStream<String, Transaction> combinedFraudStream =
                highAmountFraudStream.merge(rapidTxnFraudStream);

        // 5️⃣ Send to Fraud Alert Topic

        combinedFraudStream
                .peek((key, tx) -> {
                    log.warn("🚨 FRAUD DETECTED :: {}", tx);
                })
                .to(ALERT_TOPIC,
                        Produced.with(Serdes.String(), transactionSerde));

        return txnStream;
    }
}
