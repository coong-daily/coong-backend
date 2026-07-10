package com.coong_backend.domain.asset.entity;

import com.coong_backend.domain.asset.type.AccountStatus;
import com.coong_backend.domain.asset.type.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "bank")
    private String bank;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "currency")
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;

    @Column(name = "memo")
    private String memo;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    /**
     * 계좌 정보 수정 비즈니스 메서드 (전체 필드 반영)
     */
    public void update(String accountNumber, String accountName, Integer balance, String bank, AccountType accountType,
                       LocalDate expiryDate, String currency, AccountStatus status, String memo) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.balance = balance;
        this.bank = bank; // 이제 타입이 일치하여 정상 작동합니다.
        this.accountType = accountType;
        this.expiryDate = expiryDate;
        this.currency = currency;
        this.status = status;
        this.memo = memo;
    }
}