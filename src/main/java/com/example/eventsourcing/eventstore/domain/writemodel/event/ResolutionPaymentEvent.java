package com.example.eventsourcing.eventstore.domain.writemodel.event;

import com.example.eventsourcing.eventstore.domain.writemodel.Account;
import com.example.eventsourcing.eventstore.eventsourcing.Event;

import java.math.BigDecimal;
import java.util.List;
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
public class ResolutionPaymentEvent extends Event {

  private UUID accountId;
  private BigDecimal amount;
  private List<Account> account;

  @Builder
  public ResolutionPaymentEvent(UUID aggregateId, UUID accountId, BigDecimal amount, List<Account> account) {
    super(aggregateId);
    this.accountId = accountId;
    this.amount = amount;
    this.account = account;
  }
}
