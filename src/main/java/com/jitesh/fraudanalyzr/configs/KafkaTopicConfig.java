package com.jitesh.fraudanalyzr.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${app.topics.transactions}")
    private String transactions;

    @Value("${app.topics.fraud-alerts}")
    private String fraudAlerts;

    @Bean
    public NewTopic transactionsTopic() {
        return TopicBuilder.name(transactions).partitions(6).replicas(1).build();
    }

    @Bean
    public NewTopic fraudAlertsTopic() {
        return TopicBuilder.name(fraudAlerts).partitions(3).replicas(1).build();
    }

}
