package com.example.eventsourcing.eventstore.domain.writemodel.event;

import com.example.eventsourcing.eventstore.eventsourcing.Event;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PaymentSucceededEvent extends Event {

  private BigDecimal amount;

  @Builder
  public PaymentSucceededEvent(UUID aggregateId, BigDecimal amount) {
    super(aggregateId);
    this.amount = amount;
  }
}
