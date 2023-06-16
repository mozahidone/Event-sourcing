package com.example.eventsourcing.eventstore.service;

import com.example.eventsourcing.eventstore.domain.writemodel.Account;
import com.example.eventsourcing.eventstore.eventsourcing.Command;
import com.example.eventsourcing.eventstore.eventsourcing.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountCommandHandler {

  private final AccountEventStore eventStore;

  public void process(Command command) {
    Objects.requireNonNull(command);
    log.debug("Processing command {}", command);
    UUID accountId = command.getAggregateId();
    List<Event> events = eventStore.readEvents(accountId);
    Account account = new Account(accountId, events);
    account.process(command);
    long expectedRevision = command.getExpectedRevision();
    for (Event event : account.getChanges()) {
      expectedRevision = eventStore.append(event, expectedRevision);
    }
  }
}
