package com.jitesh.fraudanalyzr.events;

import com.jitesh.fraudanalyzr.models.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionEventConsumer {

    @KafkaListener(topics = "${app.topics.transactions}", groupId = "${app.consumer.txn.group}", properties = {"max.poll.records=1"})
    public void transactionConsumer1(Transaction txEvents, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) String offset) {
        log.info("💰 TRANSACTION RECEIVED IN CONSUMER 1 ::  {} ", txEvents);
        log.debug("ℹ TXN RECEIVED FROM TOPIC :: {} IN CONSUMER 1 AT OFFSET :: {} ", topic, offset);
    }

    @KafkaListener(topics = "${app.topics.transactions}", groupId = "${app.consumer.txn.group}", properties = {"max.poll.records=1"})
    public void transactionConsumer2(Transaction txEvents, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) String offset) {
        log.info("💰 TRANSACTION RECEIVED IN CONSUMER 2 ::  {} ", txEvents);
        log.debug("ℹ TXN RECEIVED FROM TOPIC  :: {} IN CONSUMER 2 AT OFFSET :: {} ", topic, offset);
    }

    @KafkaListener(topics = "${app.topics.transactions}", groupId = "${app.consumer.txn.group}", topicPartitions = {@TopicPartition(topic = "${app.topics.transactions}", partitions = {"6"})})
    public void transactionConsumer3(Transaction txEvents, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) String offset) {
        log.info("💰 TRANSACTION RECEIVED IN CONSUMER 3 FROM SPECIFIC PARTITION 6 ::  {} ", txEvents);
        log.debug("ℹ TXN RECEIVED FROM TOPIC  :: {} IN CONSUMER 3 AT OFFSET :: {} ", topic, offset);
    }
}
