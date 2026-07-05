package com.coong_backend.domain.asset.dto;

import com.coong_backend.domain.asset.entity.Asset;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetResponseDto {
    private Long id;
    private String name;
    private int amount;

    public AssetResponseDto(Asset asset) {
        this.id = asset.getId();
        this.name = asset.getName();
        this.amount = asset.getAmount();
    }
}
