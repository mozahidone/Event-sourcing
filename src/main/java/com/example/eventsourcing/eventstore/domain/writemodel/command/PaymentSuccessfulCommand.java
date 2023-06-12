package com.example.eventsourcing.eventstore.domain.writemodel.command;

import com.example.eventsourcing.eventstore.domain.writemodel.Account;
import com.example.eventsourcing.eventstore.eventsourcing.Command;

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
public class PaymentSuccessfulCommand extends Command {

  private UUID accountId;
  private Boolean paymentSuccessFlag;
  private List<Account> account;
  @Builder
  public PaymentSuccessfulCommand(UUID aggregateId, UUID accountId, Boolean paymentSuccessFlag, List<Account> account) {
    super(aggregateId, -1);
    this.accountId = accountId;
    this.paymentSuccessFlag = paymentSuccessFlag;
    this.account = account;
  }
}
