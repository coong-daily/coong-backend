package com.coong_backend.domain.asset.dto;

import com.coong_backend.domain.asset.entity.Account;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class AccountResponseDto {
    private Long id;
    private String bank;
    private String accountNumber;
    private int balance;
    private String accountType;
    private LocalDate expiryDate;
    private String status;
    private String memo;

    public AccountResponseDto(Account account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.bank = account.getBank();
        this.balance = account.getBalance();
        this.accountType = account.getAccountType();
        this.expiryDate = account.getExpiryDate();
        this.status = account.getStatus();
        this.memo = account.getMemo();
    }
}
