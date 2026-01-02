package com.jitesh.fraudanalyzr.streams;

import com.jitesh.fraudanalyzr.models.Item;
import com.jitesh.fraudanalyzr.models.Transaction;
import com.jitesh.fraudanalyzr.serdes.TransactionSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class ExploringStreamAllMethods {

    @Value("${app.topics.transactions}")
    private String TOPIC;

    @Value("${app.topics.fraud-alerts}")
    private String ALERT_TOPIC;

    @Autowired
    private TransactionSerde transactionSerde;

    @Bean
    public KStream<String, Transaction> txnAnalyzer(StreamsBuilder builder) {

        // Read Message From The Input Topic
        KStream<String, Transaction> txnStream =
                builder.stream(TOPIC, Consumed.with(Serdes.String(), transactionSerde));

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

//        txnStream.mapValues((key, tx) -> {
//                    return "TXN Mode is  " + tx.getType();
////                    return KeyValue.pair(tx.getAccountId(), "Spent Amount " + tx.getAmount()); // Still Only Modifies the Value not Key
//                })
//                .peek((key, val) -> {
//                    log.info("Modify Transaction Value Only By Using MapValue  :: KEY {} , VALUE {}", key, val);
//                });

//        txnStream.flatMap((key, tx) -> {
//                    List<KeyValue<String, Item>> flattened = new ArrayList<>();
//
//                    for (Item item : tx.getItems()) {
//                        flattened.add(KeyValue.pair(tx.getAccountId(), item));
//                    }
//                    return flattened;
//                })
//                .peek((key, val) -> {
//                    log.info("Flattened Nested Items  Using flatMap :: KEY {} , VALUE {}", key, val);
//                });

//        txnStream.flatMapValues(Transaction::getItems)
//        txnStream.flatMapValues(tx -> {
//                    return tx.getItems();
//                })
//                .peek((key, val) -> {
//                    log.info("Flattened Nested Items Only Values  Using flatMapValues :: KEY {} , VALUE {}", key, val);
//                });

//        KStream<String, Transaction>[] branches = txnStream.branch(
//                (key, tx) -> tx.getType().equalsIgnoreCase("Debit"),
//                (key, tx) -> tx.getType().equalsIgnoreCase("Credit"),
//                (key, tx) -> tx.getType().equalsIgnoreCase("UPI"),
//                (key, tx) -> tx.getType().equalsIgnoreCase("Wallet")
//        );
//
//        branches[0].peek(
//                        (k, tx) -> log.info("💳 Transaction Using :: {}", tx.getType())
//                )
//                .to("debit_txn", Produced.with(Serdes.String(), transactionSerde));
//
//        branches[1].peek(
//                        (k, tx) -> log.info("💳 Transaction Using :: {}", tx.getType())
//                )
//                .to("credit_txn", Produced.with(Serdes.String(), transactionSerde));
//
//        branches[2].peek(
//                        (k, tx) -> log.info("💳 Transaction Using :: {}", tx.getType())
//                )
//                .to("upi_txn", Produced.with(Serdes.String(), transactionSerde));
//
//        branches[3].peek(
//                        (k, tx) -> log.info("💳 Transaction Using :: {}", tx.getType())
//                )
//                .to("wallet_txn", Produced.with(Serdes.String(), transactionSerde));

//        txnStream
//                .groupBy((k, tx) -> tx.getLocation())
//                .count()
//                .toStream()
//                .peek((locale, freq) -> {
//                    log.info("🌏 Country :: {} has {} Transactions", locale, freq);
//                });

//        txnStream
//                .groupBy((k, tx) -> tx.getAccountId())
//                .count()
//                .toStream()
//                .peek((account, count) -> {
//                    log.info("👤 User {} Made {} Transactions", account, count);
//                });

//                txnStream
//                .groupBy((k, tx) -> tx.getAccountId())
//                .count(Materialized.as("user-txn-count-store"))
//                .toStream()
//                .peek((account, count) -> {
//                    log.info("👤 User {} Made {} Transactions", account, count);
//                });

        txnStream
                .groupBy((key, tx) -> tx.getType())
                .aggregate(
                        () -> 0.0,
                        (mode, txn, accumulator) -> accumulator + txn.getAmount(),
                        Materialized.with(Serdes.String(), Serdes.Double())
                )
                .toStream()
                .peek((mode, sum) -> {
                            log.info("💳 Payment Mode :: {} | 💰 Running Total Amount :: {}", mode, sum);
                        }
                );

        return txnStream;
    }
}
