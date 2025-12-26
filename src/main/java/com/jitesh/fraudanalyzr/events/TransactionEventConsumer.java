package com.jitesh.fraudanalyzr.events;

import com.jitesh.fraudanalyzr.models.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionEventConsumer {

//    @KafkaListener(topics = "${app.topics.transactions}", groupId = "${app.consumer.txn.group}")
//    public void transactionConsumer1(Transaction txEvents, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) String offset) {
//        log.info("💰 TRANSACTION RECEIVED IN CONSUMER 1 ::  {} ", txEvents);
//        log.debug("ℹ TXN RECEIVED FROM TOPIC :: {} IN CONSUMER 1 AT OFFSET :: {} ", topic, offset);
//    }
//
//    @KafkaListener(topics = "${app.topics.transactions}", groupId = "${app.consumer.txn.group}")
//    public void transactionConsumer2(Transaction txEvents, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) String offset) {
//        log.info("💰 TRANSACTION RECEIVED IN CONSUMER 2 ::  {} ", txEvents);
//        log.debug("ℹ TXN RECEIVED FROM TOPIC  :: {} IN CONSUMER 2 AT OFFSET :: {} ", topic, offset);
//    }
//
//    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 5000, maxDelay = 5000 * 3, multiplier = 1.5))
//    // Retry 3 times (N-1)
//    @KafkaListener(topics = "${app.topics.transactions}",
//                                            groupId = "${app.consumer.txn.group}",
//                                            topicPartitions = {@TopicPartition(topic = "${app.topics.transactions}",
//                                            partitions = {"6"})},
//                                            properties = {"max.poll.records=1"}) // To Consume One Record At a Time in Concurrent or Batch Processing
//    public void transactionConsumer3(Transaction txEvents, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) String offset) {
//        log.info("💰 TRANSACTION RECEIVED IN CONSUMER 3 FROM SPECIFIC PARTITION 6 ::  {} ", txEvents);
//        log.debug("ℹ TXN RECEIVED FROM TOPIC  :: {} IN CONSUMER 3 AT OFFSET :: {} ", topic, offset);
//    }
//
//    @DltHandler
//    public void DLTListener(Transaction txEvents, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) String offset) {
//        log.info("🔔 DLT RECEIVED :: {} , FROM ::  {}, Offset :: {} ", txEvents, topic, offset);
//    }
}
