package com.example.eventsourcing.eventstore.service;

import com.example.eventsourcing.eventstore.domain.integration.PaymentIntegrationEvent;
import com.example.eventsourcing.eventstore.domain.writemodel.Payment;
import com.example.eventsourcing.eventstore.domain.writemodel.event.PaymentSuccessfulEvent;
import com.example.eventsourcing.eventstore.eventsourcing.Event;
import com.example.eventsourcing.eventstore.mapper.PaymentMapper;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventHandler {

  private final PaymentEventStore eventStore;
  private final PaymentMapper mapper;
  private final PaymentReadModelUpdater readModelUpdater;
  private final PaymentIntegrationEventSender integrationEventSender;

  public void process(Event event) {
    Objects.requireNonNull(event);
    log.debug("Processing event {}", event);
    UUID paymentId = event.getAggregateId();
    List<Event> events = eventStore.readEvents(paymentId);
    Payment payment = new Payment(paymentId, events);
    readModelUpdater.saveOrUpdate(mapper.toReadModel(payment));
    if(event instanceof PaymentSuccessfulEvent) {
      PaymentIntegrationEvent integrationEvent = mapper.toIntegrationEvent(event, payment);
      integrationEvent.setCorrelationId(UUID.randomUUID()); // Set the correlationId field to a new UUID
      integrationEventSender.send(integrationEvent);
    }
  }
}
