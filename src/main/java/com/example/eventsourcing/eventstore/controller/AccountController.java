package com.example.eventsourcing.eventstore.controller;

import com.example.eventsourcing.eventstore.domain.readmodel.Payment;
import com.example.eventsourcing.eventstore.domain.writemodel.PaymentStatus;
import com.example.eventsourcing.eventstore.domain.writemodel.command.PaymentCreatedCommand;
import com.example.eventsourcing.eventstore.domain.writemodel.command.PaymentFailedCommand;
import com.example.eventsourcing.eventstore.domain.writemodel.command.ResolutionPaymentCommand;
import com.example.eventsourcing.eventstore.domain.writemodel.command.PaymentSuccessfulCommand;
import com.example.eventsourcing.eventstore.repository.PaymentRepository;
import com.example.eventsourcing.eventstore.service.PaymentCommandHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
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
  private final PaymentCommandHandler commandHandler;
  private final PaymentRepository paymentRepository;

  @PostMapping
  public ResponseEntity<JsonNode> postPayment(@RequestBody JsonNode request) throws IOException {
    UUID paymentId = UUID.randomUUID();
    PaymentStatus newStatus = PaymentStatus.valueOf(request.get("status").asText());

    switch (newStatus) {
      case CREATED:
        commandHandler.process(
                PaymentCreatedCommand.builder()
                        .aggregateId(paymentId)
                        .accountId(UUID.fromString(request.get("accountId").asText()))
                        .build());
        return ResponseEntity.accepted()
                .body(objectMapper.createObjectNode().put("paymentId", paymentId.toString()));
      case PAYMENT_FAILED:
        commandHandler.process(
                PaymentFailedCommand.builder()
                        .aggregateId(paymentId)
                        .accountId(UUID.fromString(request.get("accountId").asText()))
                        .paymentSuccessFlag(false)
                        .build());
        return ResponseEntity.accepted()
                .body(objectMapper.createObjectNode().put("paymentId", paymentId.toString()));
      case PAYMENT_SUCCESSFUL:
        commandHandler.process(
                PaymentSuccessfulCommand.builder()
                        .aggregateId(paymentId)
                        .accountId(UUID.fromString(request.get("accountId").asText()))
                        .paymentSuccessFlag(true)
                        .account(
                                objectMapper.readValue(
                                        objectMapper.treeAsTokens(request.get("account")), new TypeReference<>() {}))
                        .build());
        return ResponseEntity.accepted()
                .body(objectMapper.createObjectNode().put("paymentId", paymentId.toString()));
      default:
        return ResponseEntity.badRequest().build();
    }


  }

  @PatchMapping("/{paymentId}")
  public ResponseEntity<?> resolutionPayment(@PathVariable UUID paymentId, @RequestBody JsonNode request) {
    int revision = request.get("revision").asInt();
    commandHandler.process(
            ResolutionPaymentCommand.builder()
                    .aggregateId(paymentId)
                    .expectedRevision(revision)
                    .accountId(UUID.fromString(request.get("accountId").asText()))
                    .amount(new BigDecimal(request.get("amount").asText()))
                    .build());
    return ResponseEntity.accepted().build();
  }

  @GetMapping("/")
  public ResponseEntity<List<Payment>> getPayments() {
    return ResponseEntity.ok(paymentRepository.findAll());
  }

  @GetMapping("/{paymentId}")
  public ResponseEntity<Payment> getPayment(@PathVariable UUID paymentId) {
    return paymentRepository
        .findById(paymentId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
