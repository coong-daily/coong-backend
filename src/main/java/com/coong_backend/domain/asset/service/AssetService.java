package com.coong_backend.domain.asset.service;

import com.coong_backend.domain.asset.dto.AccountDto;
import com.coong_backend.domain.asset.dto.AssetDto;
import com.coong_backend.domain.asset.entity.Account;
import com.coong_backend.domain.asset.entity.Asset;
import com.coong_backend.domain.asset.repository.AssetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final AssetRepository assetRepository;

    public List<AssetDto> findAll(){
        List<Asset> assets = assetRepository.findAll();

        List<AssetDto> assetDtoList = new ArrayList<>();

        if(!assets.isEmpty()){

            assets.forEach(a -> {
                assetDtoList.add(new AssetDto(a));
            });
        }
        return assetDtoList;
    }

}
