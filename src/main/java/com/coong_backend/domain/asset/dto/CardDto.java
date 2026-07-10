package com.coong_backend.domain.asset.dto;

import com.coong_backend.domain.asset.entity.Card;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {
    private Long id;
    private String cardName;
    private String cardNumber;

    public static CardDto fromEntity(Card card) {
        return CardDto.builder()
                .id(card.getId())
                .cardName(card.getCardName())
                .cardNumber(card.getCardNumber())
                .build();
    }
}