package com.coong_backend.domain.asset.dto;

import com.coong_backend.domain.asset.type.TransactionType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
    private String name;
    private TransactionType categoryType;
}