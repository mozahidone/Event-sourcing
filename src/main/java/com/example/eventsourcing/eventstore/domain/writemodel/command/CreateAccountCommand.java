package com.example.eventsourcing.eventstore.domain.writemodel.command;

import com.example.eventsourcing.eventstore.eventsourcing.Command;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreateAccountCommand extends Command {

  private String name;
  private String address;
  @Builder
  public CreateAccountCommand(UUID aggregateId, String name, String address) {
    super(aggregateId, -1);
    this.name = name;
    this.address = address;
  }
}
