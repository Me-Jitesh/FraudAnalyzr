package com.jitesh.fraudanalyzr.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic transactionTopic() {
        return TopicBuilder.name("transactions")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
