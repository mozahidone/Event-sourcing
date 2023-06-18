package com.example.eventsourcing.eventstore.domain.writemodel;

public enum AccountStatus {
  CREATED,
  UPDATED,
  PAYMENT_FAILED,
  PAYMENT_SUCCEEDED,
  PAYMENT_POSTED
}
