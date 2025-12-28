package com.jitesh.fraudanalyzr.streams;

import com.jitesh.fraudanalyzr.models.Transaction;
import com.jitesh.fraudanalyzr.serdes.TransactionSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ExploringStreamAllMethods {

    @Value("${app.topics.transactions}")
    private String TOPIC;

    @Value("${app.topics.fraud-alerts}")
    private String ALERT_TOPIC;

    @Bean
    public KStream<String, Transaction> txnAnalyzer(StreamsBuilder builder) {

        // Read Message From The Input Topic
        KStream<String, Transaction> txnStream =
                builder.stream(TOPIC, Consumed.with(Serdes.String(), new TransactionSerde()));

        // Process The Stream

//        txnStream
//                .filterNot((key, tx) -> tx.getAmount() > 100000)
//                .peek((k, tx) -> {
//                    log.warn("☑ NORMAL TXN  :: {} {}", tx.getTransactionId(), tx.getAmount());
//                });

//        txnStream.filter((key, tx) -> tx.getAmount() > 100000)
//                .peek((k, tx) -> {
//                    log.warn("⚠ FRAUD TXN  :: {} {}", tx.getTransactionId(), tx.getAmount());
//                });

//        txnStream.map((key, tx) -> {
//                    return KeyValue.pair(tx.getAccountId(), "Spent Amount " + tx.getAmount());
//                })
//                .peek((key, val) -> {
//                    log.info("Modify Transaction Using Map  :: KEY {} , VALUE {}", key, val);
//                });

        txnStream.mapValues((key, tx) -> {
                    return "TXN Mode is  " + tx.getType();
//                    return KeyValue.pair(tx.getAccountId(), "Spent Amount " + tx.getAmount()); // Still Only Modifies the Value not Key
                })
                .peek((key, val) -> {
                    log.info("Modify Transaction Value Only By Using MapValue  :: KEY {} , VALUE {}", key, val);
                });

        return txnStream;
    }
}
