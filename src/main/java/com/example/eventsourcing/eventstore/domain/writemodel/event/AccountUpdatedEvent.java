package com.example.eventsourcing.eventstore.domain.writemodel.event;

import com.example.eventsourcing.eventstore.eventsourcing.Event;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AccountUpdatedEvent extends Event {

  private String address;

  @Builder
  public AccountUpdatedEvent(UUID aggregateId, String address) {
    super(aggregateId);
    this.address = address;
  }
}
