package com.example.eventsourcing.eventstore.mapper;

import com.example.eventsourcing.eventstore.domain.integration.PaymentIntegrationEvent;
import com.example.eventsourcing.eventstore.domain.writemodel.Payment;
import com.example.eventsourcing.eventstore.domain.writemodel.Account;
import com.example.eventsourcing.eventstore.eventsourcing.Event;
import java.time.Instant;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

  @Mapping(source = "aggregateId", target = "id")
  @Mapping(source = "baseRevision", target = "revision")
  com.example.eventsourcing.eventstore.domain.readmodel.Payment toReadModel(Payment payment);

  @Mapping(source = "payment.aggregateId", target = "paymentId")
  @Mapping(source = "event.eventType", target = "eventType")
  @Mapping(source = "event.createdDate", target = "eventTimestamp")
  @Mapping(source = "payment.baseRevision", target = "revision")
  @Mapping(source = "payment.accountId", target = "accountId")
  @Mapping(source = "payment.amount", target = "amount")
  @Mapping(source = "payment.account", target = "account")
  PaymentIntegrationEvent toIntegrationEvent(Event event, Payment payment);

  com.example.eventsourcing.eventstore.domain.readmodel.Account toReadModel(Account value);

  com.example.eventsourcing.eventstore.domain.integration.Account toIntegrationEvent(
      Account value);

  default long toEpochMilli(Instant instant) {
    return Optional.ofNullable(instant).map(Instant::toEpochMilli).orElse(0L);
  }
}
