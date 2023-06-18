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
public class PaymentAccountCommand extends Command {

  private BigDecimal amount;
  private String type;

  @Builder
  public PaymentAccountCommand(UUID aggregateId, int expectedRevision, BigDecimal amount, String type) {
    super(aggregateId, expectedRevision);
    this.amount = amount;
    this.type = type;
  }
}
