package com.coong_backend.domain.asset.service;

import com.coong_backend.domain.asset.dto.AccountDto;
import com.coong_backend.domain.asset.entity.Account;
import com.coong_backend.domain.asset.repository.AccountRepository;
import com.coong_backend.domain.asset.type.AccountStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public List<AccountDto> findAll(){
        return accountRepository.findByStatus(AccountStatus.ACTIVE).stream()
                .map(AccountDto::from)
                .toList();
    }

    @Transactional // 데이터 변경이 일어나므로 쓰기 트랜잭션 적용
    public AccountDto addAccount(AccountDto accountDto) {
        // DTO를 Entity로 변환
        Account account = accountDto.to();

        // DB에 저장
        Account savedAccount = accountRepository.save(account);

        // 저장된 엔티티를 다시 응답용 DTO로 변환하여 반환
        return AccountDto.from(savedAccount);
    }

    @Transactional
    public AccountDto updateAccount(Long id, AccountDto dto) {
        Account targetAccount = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 계좌를 찾을 수 없습니다. id=" + id));

        // 엔티티의 update 메서드에 DTO 데이터를 쏙 넣어줍니다.
        targetAccount.update(
                dto.getAccountNumber(),
                dto.getAccountName(),
                dto.getBalance(),
                dto.getBank(),
                dto.getAccountType(),
                dto.getExpiryDate(),
                dto.getCurrency(),
                dto.getStatus(),
                dto.getMemo()
        );

        return AccountDto.from(targetAccount);
    }

    public void deleteAccount(Long id){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 계좌를 찾을 수 없습니다. id=" + id));

        account.setStatus(AccountStatus.TERMINATED);
        accountRepository.save(account);
    }
}
