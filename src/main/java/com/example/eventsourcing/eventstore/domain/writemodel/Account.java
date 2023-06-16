package com.example.eventsourcing.eventstore.domain.writemodel;

import com.example.eventsourcing.eventstore.domain.writemodel.command.CreateAccountCommand;
import com.example.eventsourcing.eventstore.domain.writemodel.command.PaymentAccountCommand;
import com.example.eventsourcing.eventstore.domain.writemodel.command.UpdateAccountCommand;
import com.example.eventsourcing.eventstore.domain.writemodel.event.*;
import com.example.eventsourcing.eventstore.eventsourcing.Aggregate;
import com.example.eventsourcing.eventstore.eventsourcing.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Account extends Aggregate {

  private AccountStatus status;

  private String name;
  private String address;
  private UUID coRelationId;
  private BigDecimal amount;
  private String type;

  private Instant createdDate;
  private Instant updatedDate;
  private Instant postingDate;
  private Instant failedDate;
  private Instant paymentDate;

  public Account(UUID aggregateId) {
    super(aggregateId);
  }

  public Account(UUID accountId, List<Event> events) {
    super(accountId, events);
  }

  public void process(CreateAccountCommand command) {
    applyChange(
            AccountCreatedEvent.builder()
                    .aggregateId(aggregateId)
                    .name(command.getName())
                    .address(command.getAddress())
                    .build());
  }

  public void process(UpdateAccountCommand command) {
    applyChange(
            AccountUpdatedEvent.builder()
                    .aggregateId(aggregateId)
                    .address(command.getAddress())
                    .build());
  }

  public void process(PaymentAccountCommand command) {
    applyChange(
        PaymentPostedEvent.builder()
            .aggregateId(aggregateId)
            .amount(command.getAmount())
            .type(command.getType())
            .build());
    if (command.getType().equalsIgnoreCase("")) {
      applyChange(
              PaymentSucceededEvent.builder()
                      .aggregateId(aggregateId)
                      .amount(command.getAmount())
                      .build());
    } else {
      applyChange(
              PaymentFailedEvent.builder()
                      .aggregateId(aggregateId)
                      .amount(command.getAmount())
                      .build());
    }
  }

  public void apply(AccountCreatedEvent event) {
    this.coRelationId = event.getCoRelationId();
    this.status = AccountStatus.CREATED;
    this.name = event.getName();
    this.address = event.getAddress();
    this.createdDate = event.getCreatedDate();
  }

  public void apply(AccountUpdatedEvent event) {
    this.coRelationId = event.getCoRelationId();
    this.status = AccountStatus.UPDATED;
    this.address = event.getAddress();
    this.updatedDate = event.getCreatedDate();
  }

  public void apply(PaymentPostedEvent event) {
    this.coRelationId = event.getCoRelationId();
    this.status = AccountStatus.PAYMENT_POSTED;
    this.amount = event.getAmount();
    this.type = event.getType();
    this.postingDate = event.getCreatedDate();
  }

  public void apply(PaymentSucceededEvent event) {
    this.coRelationId = event.getCoRelationId();
    this.status = AccountStatus.PAYMENT_SUCCEEDED;
    this.amount = event.getAmount();
    this.paymentDate = event.getCreatedDate();
  }

  public void apply(PaymentFailedEvent event) {
    this.coRelationId = event.getCoRelationId();
    this.status = AccountStatus.PAYMENT_FAILED;
    this.amount = event.getAmount();
    this.failedDate = event.getCreatedDate();
  }
}
