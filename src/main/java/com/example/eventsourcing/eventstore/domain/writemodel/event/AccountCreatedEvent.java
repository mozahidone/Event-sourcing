package com.example.eventsourcing.eventstore.domain.writemodel.event;

import com.example.eventsourcing.eventstore.eventsourcing.Event;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AccountCreatedEvent extends Event {

  private String name;
  private String address;

  @Builder
  public AccountCreatedEvent(UUID aggregateId, String name, String address) {
    super(aggregateId);
    this.name = name;
    this.address = address;
  }
}
