package com.example.eventsourcing.eventstore.service;

import com.example.eventsourcing.eventstore.config.KafkaTopicsConfig;
import com.example.eventsourcing.eventstore.domain.integration.PaymentIntegrationEvent;
import com.example.eventsourcing.eventstore.domain.readmodel.Payment;
import com.example.eventsourcing.eventstore.domain.writemodel.PaymentStatus;
import com.example.eventsourcing.eventstore.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;




@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentIntegrationEventConsumer {
    private final ObjectMapper objectMapper;
    private final PaymentRepository paymentRepository;

    @KafkaListener(topics = KafkaTopicsConfig.TOPIC_PAYMENT_INTEGRATION_EVENTS, groupId = "my-group")
    public void listen(String record) {

        try {
            PaymentIntegrationEvent event = objectMapper.readValue(record, PaymentIntegrationEvent.class);
            log.debug("Received integration event: {}", event);
            // Process the payment integration event here
            if(event.getEventType().equalsIgnoreCase(PaymentStatus.PAYMENT_SUCCESSFUL.name())) {
                Payment payment = paymentRepository.findById(event.getPaymentId()).get();
                payment.getAccount().stream()
                        .filter(account -> account.getAccountId().equals(payment.getAccountId()))
                        .peek(account -> account.setBalanceDue(account.getBalanceDue() + payment.getAmount().doubleValue()));

                paymentRepository.save(payment);
            }
        } catch (Exception e) {
            log.error("Error processing integration event: {}", e.getMessage(), e);
        }
    }
}

/*public class PaymentIntegrationEventConsumer {

    private final ObjectMapper objectMapper;
    private final PaymentRepository paymentRepository;

    @PostConstruct
    public void start() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singleton(KafkaTopicsConfig.TOPIC_PAYMENT_INTEGRATION_EVENTS));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                try {
                    PaymentIntegrationEvent event = objectMapper.readValue(record.value(), PaymentIntegrationEvent.class);
                    log.debug("Received integration event: {}", event);
                    // Process the payment integration event here
                    if(event.getEventType().equalsIgnoreCase(PaymentStatus.PAYMENT_SUCCESSFUL.name())) {
                        Payment payment = paymentRepository.findById(event.getPaymentId()).get();
                        payment.getAccount().stream()
                                .filter(account -> account.getAccountId().equals(payment.getAccountId()))
                                .peek(account -> account.setBalanceDue(account.getBalanceDue() + payment.getAmount().doubleValue()));

                        paymentRepository.save(payment);
                    }
                } catch (Exception e) {
                    log.error("Error processing integration event: {}", e.getMessage(), e);
                }
            }
        }
    }
}*/