package com.coong_backend.domain.asset.dto;

import com.coong_backend.domain.asset.entity.Transaction;
import com.coong_backend.domain.asset.type.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Long id;
    private LocalDateTime transactionDate;
    private int amount;
    private TransactionType transactionType;
    private String content;
    private String title;

    // 연관관계는 프론트에서 다루기 쉽도록 FK(id)만 노출
    private Long categoryId;
    private String categoryName;

    private Long accountId;
    private Long cardId;

    private String accountName;
    private String cardName;

    // entity -> dto
    public static TransactionDto from(Transaction entity) {
        return TransactionDto.builder()
                .id(entity.getId())
                .transactionDate(entity.getTransactionDate())
                .amount(entity.getAmount())
                .transactionType(entity.getTransactionType())
                .content(entity.getContent())
                .title(entity.getTitle())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName() : null)
                .accountId(entity.getAccount() != null ? entity.getAccount().getId() : null)
                .cardId(entity.getCard() != null ? entity.getCard().getId() : null)
                .accountName(entity.getAccount() != null ? entity.getAccount().getBank() : null)
                .cardName(entity.getCard() != null ? entity.getCard().getCardName() : null)
                .build();
    }
}