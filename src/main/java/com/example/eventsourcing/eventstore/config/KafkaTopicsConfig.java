package com.example.eventsourcing.eventstore.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

  public static final String TOPIC_PAYMENT_INTEGRATION_EVENTS = "payment-integration-events";

  @Bean
  public NewTopic orderIntegrationEventsTopic() {
    return TopicBuilder.name(TOPIC_PAYMENT_INTEGRATION_EVENTS).partitions(10).replicas(1).build();
  }
}
