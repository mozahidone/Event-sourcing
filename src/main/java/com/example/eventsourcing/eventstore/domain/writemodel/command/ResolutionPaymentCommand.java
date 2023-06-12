package com.example.eventsourcing.eventstore.domain.writemodel.command;

import com.example.eventsourcing.eventstore.eventsourcing.Command;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResolutionPaymentCommand extends Command {

  private UUID accountId;
  private BigDecimal amount;

  @Builder
  public ResolutionPaymentCommand(UUID aggregateId, int expectedRevision, UUID accountId, BigDecimal amount) {
    super(aggregateId, expectedRevision);
    this.accountId = accountId;
    this.amount = amount;
  }
}
