package com.coong_backend.domain.asset.repository;

import com.coong_backend.domain.asset.entity.Account;
import com.coong_backend.domain.asset.type.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByStatus(AccountStatus status);
}