package com.example.eventsourcing.eventstore.domain.writemodel.event;

import com.example.eventsourcing.eventstore.eventsourcing.Event;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PaymentPostedEvent extends Event {

  private BigDecimal amount;
  private String type;

  @Builder
  public PaymentPostedEvent(UUID aggregateId, BigDecimal amount, String type) {
    super(aggregateId);
    this.amount = amount;
    this.type = type;
  }
}
