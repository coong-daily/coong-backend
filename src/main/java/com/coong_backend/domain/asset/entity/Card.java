package com.coong_backend.domain.asset.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "benefit_condition")
    private Integer benefitCondition;

    @Column(name = "card_company")
    private String cardCompany;

    @Column(name = "payment_day")
    private LocalDate paymentDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "card")
    private List<Transaction> transactions;

}
