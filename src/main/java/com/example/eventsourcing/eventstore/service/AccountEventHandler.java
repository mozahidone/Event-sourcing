package com.example.eventsourcing.eventstore.service;

import com.example.eventsourcing.eventstore.domain.integration.PaymentIntegrationEvent;
import com.example.eventsourcing.eventstore.domain.writemodel.Account;
import com.example.eventsourcing.eventstore.domain.writemodel.event.PaymentPostedEvent;
import com.example.eventsourcing.eventstore.domain.writemodel.event.PaymentSucceededEvent;
import com.example.eventsourcing.eventstore.eventsourcing.Event;
import com.example.eventsourcing.eventstore.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountEventHandler {

  private final AccountEventStore eventStore;
  private final AccountMapper mapper;
  private final PaymentReadModelUpdater readModelUpdater;
  private final PaymentIntegrationEventSender integrationEventSender;

  public void process(Event event) {
    Objects.requireNonNull(event);
    log.debug("Processing event {}", event);
    UUID accountId = event.getAggregateId();
    List<Event> events = eventStore.readEvents(accountId);
    Account account = new Account(accountId, events);
    readModelUpdater.saveOrUpdate(mapper.toReadModel(account));
    if(event instanceof PaymentSucceededEvent) {
      PaymentIntegrationEvent integrationEvent = mapper.toIntegrationEvent(event, account);
      integrationEventSender.send(integrationEvent);
    }
  }
}
