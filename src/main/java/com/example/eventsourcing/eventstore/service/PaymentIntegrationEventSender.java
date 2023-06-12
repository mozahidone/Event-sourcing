package com.example.eventsourcing.eventstore.service;

import com.example.eventsourcing.eventstore.config.KafkaTopicsConfig;
import com.example.eventsourcing.eventstore.domain.integration.PaymentIntegrationEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentIntegrationEventSender {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  @SneakyThrows
  public void send(PaymentIntegrationEvent event) {
    Objects.requireNonNull(event);
    log.debug("Publishing integration event {}", event);
    kafkaTemplate.send(
        KafkaTopicsConfig.TOPIC_PAYMENT_INTEGRATION_EVENTS,
        event.getPaymentId().toString(),
        objectMapper.writeValueAsString(event));
  }
}
