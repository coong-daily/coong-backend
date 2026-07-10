package com.coong_backend.domain.asset.service;

import com.coong_backend.domain.asset.dto.CardDto;
import com.coong_backend.domain.asset.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public List<CardDto> findAll() {
        return cardRepository.findAll().stream()
                .map(CardDto::fromEntity)
                .collect(Collectors.toList());
    }
}