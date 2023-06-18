package com.example.eventsourcing.eventstore.domain.integration;

import com.example.eventsourcing.eventstore.domain.writemodel.AccountStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class PaymentIntegrationEvent {

  @JsonProperty("account_id")
  UUID accountId;

  @JsonProperty("event_type")
  String eventType;

  @JsonProperty("event_timestamp")
  long eventTimestamp;

  @JsonProperty("revision")
  int revision;

  @JsonProperty("status")
  AccountStatus status;

  @JsonProperty("amount")
  BigDecimal amount;

  @JsonProperty("correlation_id")
  UUID correlationId;
}
