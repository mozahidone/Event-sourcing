package com.example.eventsourcing.eventstore.domain.writemodel.command;

import com.example.eventsourcing.eventstore.eventsourcing.Command;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdateAccountCommand extends Command {

  private String address;
  @Builder
  public UpdateAccountCommand(UUID aggregateId, int expectedRevision, String address) {
    super(aggregateId, expectedRevision);
    this.address = address;
  }
}
