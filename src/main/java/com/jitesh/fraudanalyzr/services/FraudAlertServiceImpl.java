package com.jitesh.fraudanalyzr.services;

import com.jitesh.fraudanalyzr.models.FraudAlert;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class FraudAlertServiceImpl {

    private static final String ALERT_KEY = "fraud:alerts";

    @Autowired
    private RedisTemplate<String, FraudAlert> redisTemplate;

    @KafkaListener(topics = "${app.topics.fraud-alerts}", groupId = "${app.consumer.txn.fraud.group}")
    public void consume(FraudAlert alert) {

        try {
            // Push alert to Redis list (right side = newest)
            redisTemplate.opsForList().rightPush(ALERT_KEY, alert);

            // Keep only last 1000 alerts (prevent unlimited growth)
            redisTemplate.opsForList().trim(ALERT_KEY, -1000, -1);

            // Expire entire list after 7 Days
            redisTemplate.expire(ALERT_KEY, Duration.ofHours(24 * 7));

            log.warn("🚨 FRAUD ALERT RECEIVED :: {}", alert);

        } catch (Exception e) {
            log.error("Failed to store fraud alert in Redis", e);
        }
    }

    public List<FraudAlert> getAlerts() {

        List<FraudAlert> alerts =
                redisTemplate.opsForList().range(ALERT_KEY, -100, -1);

        return alerts != null ? alerts : List.of();
    }
}