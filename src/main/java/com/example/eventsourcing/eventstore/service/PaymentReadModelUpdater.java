package com.example.eventsourcing.eventstore.service;

import com.example.eventsourcing.eventstore.repository.AccountRepository;
import com.example.eventsourcing.eventstore.domain.readmodel.Account;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentReadModelUpdater {

  private final AccountRepository repository;

  public void saveOrUpdate(Account account) {
    Objects.requireNonNull(account);
    log.debug("Updating read model for payment {}", account);
    repository.save(account);
  }
}
