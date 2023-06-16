package com.example.eventsourcing.eventstore.repository;

import com.example.eventsourcing.eventstore.domain.readmodel.Account;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, UUID> {}
