package com.coong_backend.domain.asset.dto;

import com.coong_backend.domain.asset.entity.Account;
import com.coong_backend.domain.asset.type.AccountStatus;
import com.coong_backend.domain.asset.type.AccountType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String accountNumber;
    private String accountName;
    private int balance;
    private String bank;          // JSON 요청의 "KB" 문자열이 자동으로 Enum으로 매핑됩니다.
    private AccountType accountType;
    private LocalDate expiryDate;
    private String currency;
    private AccountStatus status;
    private String memo;

    // entity > dto
    public static AccountDto from(Account entity) {
        return AccountDto.builder()
                .id(entity.getId())
                .accountNumber(entity.getAccountNumber())
                .bank(entity.getBank()) // 한글명 변환
                .balance(entity.getBalance())
                .accountType(entity.getAccountType())
                .expiryDate(entity.getExpiryDate())
                .status(entity.getStatus())
                .memo(entity.getMemo())
                .currency(entity.getCurrency())
                .build();
    }

    // dto > entity
    public Account to(){
        return Account.builder()
                .accountNumber(this.accountNumber)
                .balance(this.balance)
                .bank(this.bank)
                .accountType(this.accountType)
                .expiryDate(this.expiryDate)
                .currency(this.currency)
                .status(this.status)
                .memo(this.memo)
                .build();
    }
}
