package com.example.eventsourcing.eventstore.service;

import com.example.eventsourcing.eventstore.config.KafkaTopicsConfig;
import com.example.eventsourcing.eventstore.domain.integration.PaymentIntegrationEvent;
import com.example.eventsourcing.eventstore.domain.readmodel.Account;
import com.example.eventsourcing.eventstore.domain.readmodel.Payment;
import com.example.eventsourcing.eventstore.domain.writemodel.AccountStatus;
import com.example.eventsourcing.eventstore.domain.writemodel.event.PaymentSucceededEvent;
import com.example.eventsourcing.eventstore.repository.AccountRepository;
import com.example.eventsourcing.eventstore.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentIntegrationEventConsumer {
    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;

    @KafkaListener(topics = KafkaTopicsConfig.TOPIC_PAYMENT_INTEGRATION_EVENTS, groupId = "my-group")
    public void listen(String record) {

        try {
            PaymentIntegrationEvent event = objectMapper.readValue(record, PaymentIntegrationEvent.class);
            log.debug("Received integration event: {}", event);
            if(event.getEventType().equalsIgnoreCase("PaymentSucceededEvent")) {
                Account account = accountRepository.findById(event.getAccountId()).get();
                account.setBalance(account.getBalance().add(event.getAmount()));
                accountRepository.save(account);

                Payment payment = new Payment();
                payment.setId(UUID.randomUUID());
                payment.setPaymentDate(event.getEventTimestamp());
                payment.setAccountId(event.getAccountId());
                payment.setAmount(event.getAmount());
                payment.setCorrelationId(event.getCorrelationId());
                paymentRepository.save(payment);
            }
        } catch (Exception e) {
            log.error("Error processing integration event: {}", e.getMessage(), e);
        }

        /*try {
            PaymentIntegrationEvent event = objectMapper.readValue(record, PaymentIntegrationEvent.class);
            log.debug("Received integration event: {}", event);
            // Process the payment integration event here
            if(event.getEventType().equalsIgnoreCase(AccountStatus.PAYMENT_SUCCESSFUL.name())) {
                Account payment = accountRepository.findById(event.getPaymentId()).get();
                payment.getAccount().stream()
                        .filter(account -> account.getAccountId().equals(payment.getAccountId()))
                        .peek(account -> account.setBalanceDue(account.getBalanceDue() + payment.getAmount().doubleValue()));

                accountRepository.save(payment);
            }
        } catch (Exception e) {
            log.error("Error processing integration event: {}", e.getMessage(), e);
        }*/
    }
}