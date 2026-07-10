package com.coong_backend.domain.asset.controller;

import com.coong_backend.domain.asset.dto.*;
import com.coong_backend.domain.asset.service.*;
import com.coong_backend.domain.asset.type.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/asset")
@RequiredArgsConstructor
public class AssetController {
    private final AssetService assetService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final CardService cardService;

    /**
     * 총 자산 조회
     */
    @GetMapping("/total")
    public ResponseEntity<List<AssetDto>> getAllAssets() {
         List<AssetDto> assets = assetService.findAll();

        return ResponseEntity.ok().body(assets);
    }

    /**
     * 총 계좌 조회 (활동 상태)
     */
    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountService.findAll();

        return ResponseEntity.ok().body(accounts);
    }

    /**
     * 계좌 추가
     */
    @PostMapping("/account")
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto account) {
        AccountDto responseDto = accountService.addAccount(account);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    /**
     * 계좌 수정
     */
    @PutMapping("/account/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody AccountDto account) {        System.out.println("server");
        System.out.println("server");

        AccountDto updatedAccount = accountService.updateAccount(id, account);

        return ResponseEntity.ok().body(updatedAccount);
    }

    /**
     * 계좌 삭제
     */
    @DeleteMapping("/account/{id}")
    public ResponseEntity<List<AccountDto>> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);

        // 남아있는 최신 계좌 목록 조회
        List<AccountDto> updatedAccounts = accountService.findAll();

        return ResponseEntity.ok(updatedAccounts);
    }

    /**
     * 일별 거래 내역 조회
     */
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDto>> getDailyTransaction(
            @RequestParam("day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        List<TransactionDto> transactions = transactionService.getDailyTransactions(day);
        return ResponseEntity.ok(transactions);
    }

    /**
     * 카드 목록 조회
     */
    @GetMapping("/cards")
    public ResponseEntity<List<CardDto>> getAllCards() {
        return ResponseEntity.ok(cardService.findAll());
    }

    /**
     * 수입 카테고리 목록 조회
     */
    @GetMapping("/categories/income")
    public ResponseEntity<List<CategoryDto>> getIncomeCategories() {
        return ResponseEntity.ok(categoryService.findAllIncomeCategories());
    }

    /**
     * 지출 카테고리 목록 조회
     */
    @GetMapping("/categories/expense")
    public ResponseEntity<List<CategoryDto>> getExpenseCategories() {
        return ResponseEntity.ok(categoryService.findAllExpenseCategories());
    }


    /**
     * 거래 추가 (수입/지출 공통)
     * 프론트에서 POST /asset/transaction 으로 호출합니다.
     */
    @PostMapping("/transaction")
    public ResponseEntity<TransactionDto> addTransaction(@RequestBody TransactionDto transaction) {
        TransactionDto saved = transactionService.addTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * 수입 거래 추가 (하위 호환용, 필요 없으면 삭제 가능)
     */
    @PostMapping("/transaction/income")
    public ResponseEntity<TransactionDto> addIncomeTransaction(@RequestParam TransactionDto transaction) {
        transaction.setTransactionType(TransactionType.INCOME);
        TransactionDto saved = transactionService.addTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * 지출 거래 추가
     */
    @PostMapping("/transaction/expense")
    public ResponseEntity<TransactionDto> addExpenseTransaction(@RequestBody TransactionDto transaction) {
        transaction.setTransactionType(TransactionType.EXPENSE);
        TransactionDto saved = transactionService.addTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }



}
