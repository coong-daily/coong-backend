package com.coong_backend.domain.asset.service;

import com.coong_backend.domain.asset.dto.AssetResponseDto;
import com.coong_backend.domain.asset.entity.Asset;
import com.coong_backend.domain.asset.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final AssetRepository assetRepository;

    public List<AssetResponseDto> findAll(){
        List<Asset> assets = assetRepository.findAll();

        List<AssetResponseDto> assetDtoList = new ArrayList<>();

        if(!assets.isEmpty()){

            assets.forEach(a -> {
                assetDtoList.add(new AssetResponseDto(a));
            });
        }
        return assetDtoList;
    }
}
