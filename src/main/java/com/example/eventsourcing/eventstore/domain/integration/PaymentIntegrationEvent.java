package com.example.eventsourcing.eventstore.domain.integration;

import com.example.eventsourcing.eventstore.domain.writemodel.AccountStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Value
@Builder
public class PaymentIntegrationEvent {

  @JsonProperty("payment_id")
  UUID paymentId;

  @JsonProperty("event_type")
  String eventType;

  @JsonProperty("event_timestamp")
  long eventTimestamp;

  @JsonProperty("revision")
  int revision;

  @JsonProperty("status")
  AccountStatus status;

  @JsonProperty("account_id")
  UUID accountId;

  @JsonProperty("amount")
  BigDecimal amount;

  @JsonProperty("account")
  List<Account> account;

  @JsonProperty("correlation_id")
  UUID correlationId;
}
