package com.example.eventsourcing.eventstore.domain.writemodel.command;

import com.example.eventsourcing.eventstore.eventsourcing.Command;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PaymentFailedCommand extends Command {

  private UUID accountId;
  private Boolean paymentSuccessFlag;
  @Builder
  public PaymentFailedCommand(UUID aggregateId, UUID accountId, Boolean paymentSuccessFlag) {
    super(aggregateId, -1);
    this.accountId = accountId;
    this.paymentSuccessFlag = paymentSuccessFlag;
  }
}
