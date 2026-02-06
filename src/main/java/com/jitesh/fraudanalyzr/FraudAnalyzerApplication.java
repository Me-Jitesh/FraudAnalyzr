package com.jitesh.fraudanalyzr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class FraudAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FraudAnalyzerApplication.class, args);
	}

}
