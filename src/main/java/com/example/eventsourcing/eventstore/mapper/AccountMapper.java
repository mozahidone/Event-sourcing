package com.example.eventsourcing.eventstore.mapper;

import com.example.eventsourcing.eventstore.domain.integration.PaymentIntegrationEvent;
import com.example.eventsourcing.eventstore.domain.writemodel.Account;
import com.example.eventsourcing.eventstore.eventsourcing.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface AccountMapper {

  @Mapping(source = "aggregateId", target = "id")
  @Mapping(source = "baseRevision", target = "revision")
  com.example.eventsourcing.eventstore.domain.readmodel.Account toReadModel(Account account);

  @Mapping(source = "account.aggregateId", target = "paymentId")
  @Mapping(source = "event.eventType", target = "eventType")
  @Mapping(source = "event.createdDate", target = "eventTimestamp")
  @Mapping(source = "account.baseRevision", target = "revision")
  @Mapping(source = "account.accountId", target = "accountId")
  @Mapping(source = "payment.amount", target = "amount")
  PaymentIntegrationEvent toIntegrationEvent(Event event, Account account);

/*  com.example.eventsourcing.eventstore.domain.readmodel.Account toReadModel(Account value);

  com.example.eventsourcing.eventstore.domain.integration.Account toIntegrationEvent(
      Account value);*/

  default long toEpochMilli(Instant instant) {
    return Optional.ofNullable(instant).map(Instant::toEpochMilli).orElse(0L);
  }
}
