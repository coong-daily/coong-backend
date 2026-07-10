package com.coong_backend.domain.asset.repository;

import com.coong_backend.domain.asset.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByTransactionDateBetween(LocalDateTime start, LocalDateTime end);
}