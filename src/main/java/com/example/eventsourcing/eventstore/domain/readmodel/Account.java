package com.example.eventsourcing.eventstore.domain.readmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class Account implements Serializable {

  private UUID accountId;
  private String name;
  private String address;

  @JsonProperty("balance_due")
  private double balanceDue;
}
