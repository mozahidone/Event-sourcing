package com.example.eventsourcing.eventstore.domain.writemodel.event;

import com.example.eventsourcing.eventstore.eventsourcing.Event;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PaymentFailedEvent extends Event {

  private UUID accountId;
  private Boolean paymentSuccessFlag;

  @Builder
  public PaymentFailedEvent(UUID aggregateId, UUID accountId, Boolean paymentSuccessFlag) {
    super(aggregateId);
    this.accountId = accountId;
    this.paymentSuccessFlag = paymentSuccessFlag;
  }
}
