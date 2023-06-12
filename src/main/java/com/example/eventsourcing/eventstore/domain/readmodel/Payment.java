package com.example.eventsourcing.eventstore.domain.readmodel;

import com.example.eventsourcing.eventstore.domain.writemodel.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "PAYMENTS")
@Data
@EqualsAndHashCode(exclude = "id")
public class Payment implements Persistable<UUID>, Serializable {

  @Id private UUID id;
  private int revision;

  @Enumerated(EnumType.STRING)
  private PaymentStatus status;

  private UUID accountId;
  private BigDecimal amount;
  private Instant createdDate;
  private Instant paymentDate;
  private Instant failedDate;
  private Instant resolutionDate;
  @ElementCollection private List<Account> account = new ArrayList<>();

  @JsonIgnore
  @Override
  public boolean isNew() {
    return revision <= 0;
  }
}
