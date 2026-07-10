package com.coong_backend.domain.asset.service;

import com.coong_backend.domain.asset.dto.TransactionDto;
import com.coong_backend.domain.asset.entity.Account;
import com.coong_backend.domain.asset.entity.Card;
import com.coong_backend.domain.asset.entity.Category;
import com.coong_backend.domain.asset.entity.Transaction;
import com.coong_backend.domain.asset.repository.AccountRepository;
import com.coong_backend.domain.asset.repository.CardRepository;
import com.coong_backend.domain.asset.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final CategoryService categoryService;

    @Transactional
    public TransactionDto addTransaction(TransactionDto dto) {
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계좌입니다."));

        // 카드 없는 거래(계좌이체 등)를 허용하려면 optional로 처리
        Card card = null;
        if (dto.getCardId() != null) {
            card = cardRepository.findById(dto.getCardId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드입니다."));
        }

        Category category = categoryService.resolveCategory(dto.getTransactionType(), dto.getCategoryId());

        Transaction transaction = Transaction.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .amount(dto.getAmount())
                .transactionType(dto.getTransactionType())
                .transactionDate(dto.getTransactionDate())
                .category(category)
                .account(account)
                .card(card)
                .build();

        Transaction saved = transactionRepository.save(transaction);
        return TransactionDto.from(saved);
    }

    @Transactional(readOnly = true)
    public List<TransactionDto> getDailyTransactions(LocalDate day) {
        LocalDateTime start = day.atStartOfDay();
        LocalDateTime end = day.plusDays(1).atStartOfDay();

        return transactionRepository.findAllByTransactionDateBetween(start, end)
                .stream()
                .map(TransactionDto::from)
                .collect(Collectors.toList());
    }
}