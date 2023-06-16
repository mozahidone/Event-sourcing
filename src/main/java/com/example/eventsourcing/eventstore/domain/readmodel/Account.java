package com.example.eventsourcing.eventstore.domain.readmodel;

import com.example.eventsourcing.eventstore.domain.writemodel.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "PAYMENTS")
@Data
@EqualsAndHashCode(exclude = "id")
public class Account implements Persistable<UUID>, Serializable {

  @Id private UUID id;
  private int revision;

  @Enumerated(EnumType.STRING)
  private AccountStatus status;

  private UUID accountId;
  private String name;
  private String address;
  private BigDecimal balance;

  @JsonIgnore
  @Override
  public boolean isNew() {
    return revision <= 0;
  }
}
