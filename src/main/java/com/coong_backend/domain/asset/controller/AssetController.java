package com.coong_backend.domain.asset.controller;

import com.coong_backend.domain.asset.dto.AccountResponseDto;
import com.coong_backend.domain.asset.dto.AssetResponseDto;
import com.coong_backend.domain.asset.service.AccountService;
import com.coong_backend.domain.asset.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/asset")
@RequiredArgsConstructor
public class AssetController {
    private final AssetService assetService;
    private final AccountService accountService;

    /**
     * 총 자산 조회
     */
    @GetMapping("/total")
    public ResponseEntity<List<AssetResponseDto>> getAllAssets() {
         List<AssetResponseDto> assets = assetService.findAll();

        return ResponseEntity.ok().body(assets);
    }

    /**
     * 총 계좌 조회
     */
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
        List<AccountResponseDto> accounts = accountService.findAll();

        return ResponseEntity.ok().body(accounts);
    }

}
