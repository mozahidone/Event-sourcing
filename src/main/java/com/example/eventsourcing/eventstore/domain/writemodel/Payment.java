package com.example.eventsourcing.eventstore.domain.writemodel;

import com.example.eventsourcing.eventstore.domain.writemodel.command.PaymentCreatedCommand;
import com.example.eventsourcing.eventstore.domain.writemodel.command.PaymentFailedCommand;
import com.example.eventsourcing.eventstore.domain.writemodel.command.PaymentSuccessfulCommand;
import com.example.eventsourcing.eventstore.domain.writemodel.command.ResolutionPaymentCommand;
import com.example.eventsourcing.eventstore.domain.writemodel.event.PaymentCreatedEvent;
import com.example.eventsourcing.eventstore.domain.writemodel.event.PaymentFailedEvent;
import com.example.eventsourcing.eventstore.domain.writemodel.event.PaymentSuccessfulEvent;
import com.example.eventsourcing.eventstore.domain.writemodel.event.ResolutionPaymentEvent;
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
public class Payment extends Aggregate {

  private PaymentStatus status;
  private UUID accountId;
  private BigDecimal amount;

  private List<Account> account;
  private Instant createdDate;
  private Instant paymentDate;
  private Instant failedDate;
  private Instant resolutionDate;

  public Payment(UUID aggregateId) {
    super(aggregateId);
  }

  public Payment(UUID orderId, List<Event> events) {
    super(orderId, events);
  }

  public void process(PaymentCreatedCommand command) {
    applyChange(
            PaymentCreatedEvent.builder()
                    .aggregateId(aggregateId)
                    .accountId(command.getAccountId())
                    .paymentSuccessFlag(command.getPaymentSuccessFlag())
                    .build());
  }

  public void process(PaymentSuccessfulCommand command) {
    applyChange(
        PaymentSuccessfulEvent.builder()
            .aggregateId(aggregateId)
            .accountId(command.getAccountId())
            .paymentSuccessFlag(command.getPaymentSuccessFlag())
            .account(command.getAccount())
            .build());
  }

  public void process(PaymentFailedCommand command) {
    applyChange(
            PaymentFailedEvent.builder()
                    .aggregateId(aggregateId)
                    .accountId(command.getAccountId())
                    .paymentSuccessFlag(command.getPaymentSuccessFlag())
                    .build());
  }

  public void process(ResolutionPaymentCommand command) {
    applyChange(
        ResolutionPaymentEvent.builder()
            .aggregateId(aggregateId)
            .accountId(command.getAccountId())
            .amount(command.getAmount())
            .build());
  }

  public void apply(PaymentCreatedEvent event) {
    this.status = PaymentStatus.CREATED;
    this.accountId = event.getAccountId();
    this.createdDate = event.getCreatedDate();
  }

  public void apply(PaymentSuccessfulEvent event) {
    this.status = PaymentStatus.PAYMENT_SUCCESSFUL;
    this.accountId = event.getAccountId();
    this.paymentDate = event.getCreatedDate();
    this.account = event.getAccount();
  }

  public void apply(PaymentFailedEvent event) {
    this.status = PaymentStatus.PAYMENT_FAILED;
    this.accountId = event.getAccountId();
    this.failedDate = event.getCreatedDate();
  }

  public void apply(ResolutionPaymentEvent event) {
    this.status = PaymentStatus.PAYMENT_RESOLUTION;
    this.accountId = event.getAccountId();
    this.amount = event.getAmount();
    this.resolutionDate = event.getCreatedDate();
  }

}
