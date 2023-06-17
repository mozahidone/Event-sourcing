package com.example.eventsourcing.eventstore.domain.readmodel;

import com.example.eventsourcing.eventstore.domain.writemodel.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "PAYMENTS")
@Data
@EqualsAndHashCode(exclude = "id")
public class Payment implements Persistable<UUID>, Serializable {

  @Id private UUID id;
  private int revision;

  private UUID accountId;

  private UUID coRelationId;

  @Enumerated(EnumType.STRING)
  private AccountStatus status;

  long paymentDate;

  private BigDecimal amount;

  @JsonIgnore
  @Override
  public boolean isNew() {
    return revision <= 0;
  }
}
