package com.example.eventsourcing.eventstore.controller;

import com.example.eventsourcing.eventstore.domain.readmodel.Account;
import com.example.eventsourcing.eventstore.domain.readmodel.Payment;
import com.example.eventsourcing.eventstore.domain.writemodel.command.*;
import com.example.eventsourcing.eventstore.repository.AccountRepository;
import com.example.eventsourcing.eventstore.repository.PaymentRepository;
import com.example.eventsourcing.eventstore.service.AccountCommandHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

  private final ObjectMapper objectMapper;
  private final AccountCommandHandler commandHandler;
  private final AccountRepository accountRepository;
  private final PaymentRepository paymentRepository;

  @PostMapping
  public ResponseEntity<JsonNode> createAccount(@RequestBody Map<String, String> request) throws IOException {
    UUID accountId = UUID.randomUUID();
    commandHandler.process(
            CreateAccountCommand.builder()
                    .aggregateId(accountId)
                    .name(request.get("name"))
                    .address(request.get("address"))
                    .build());
    return ResponseEntity.accepted()
            .body(objectMapper.createObjectNode().put("accountId", accountId.toString()));

  }

  @PatchMapping("/{accountId}")
  public ResponseEntity<?> updateAccount(@PathVariable UUID accountId, @RequestBody JsonNode request) {
    int revision = request.get("revision").asInt();
    commandHandler.process(
            UpdateAccountCommand.builder()
                    .aggregateId(accountId)
                    .expectedRevision(revision)
                    .address(request.get("address").asText())
                    .build());
    return ResponseEntity.accepted().build();
  }

  @PatchMapping("/payment/{accountId}")
  public ResponseEntity<?> paymentAccount(@PathVariable UUID accountId, @RequestBody JsonNode request) {
    int revision = request.get("revision").asInt();
    commandHandler.process(
            PaymentAccountCommand.builder()
                    .aggregateId(accountId)
                    .expectedRevision(revision)
                    .amount(new BigDecimal(request.get("amount").asText()))
                    .type(request.get("type").asText())
                    .build());
    return ResponseEntity.accepted().build();
  }

  @GetMapping("/")
  public ResponseEntity<List<Account>> getAccounts() {
    return ResponseEntity.ok(accountRepository.findAll());
  }

  @GetMapping("/{accountId}")
  public ResponseEntity<Account> getAccount(@PathVariable UUID accountId) {
    return accountRepository
        .findById(accountId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/payment")
  public ResponseEntity<List<Payment>> getPayments() {
    return ResponseEntity.ok(paymentRepository.findTop5ByOrderByPaymentDateDesc());
  }
}
