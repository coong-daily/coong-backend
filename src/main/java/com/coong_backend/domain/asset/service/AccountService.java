package com.coong_backend.domain.asset.service;

import com.coong_backend.domain.asset.dto.AccountResponseDto;
import com.coong_backend.domain.asset.entity.Account;
import com.coong_backend.domain.asset.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public List<AccountResponseDto> findAll(){
        List<Account> accounts = accountRepository.findAll();

        List<AccountResponseDto> acountDtoList = new ArrayList<>();

        if(!accounts.isEmpty()){

            accounts.forEach(a -> {
                acountDtoList.add(new AccountResponseDto(a));
            });
        }
        return acountDtoList;
    }
}
