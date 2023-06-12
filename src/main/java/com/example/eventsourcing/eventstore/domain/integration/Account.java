package com.example.eventsourcing.eventstore.domain.integration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.UUID;

@Value
public class Account {

  private UUID accountId;
  private String name;
  private String address;

  @JsonProperty("balance_due")
  private double balanceDue;
}
