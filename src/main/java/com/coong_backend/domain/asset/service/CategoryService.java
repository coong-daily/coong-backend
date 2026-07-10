package com.coong_backend.domain.asset.service;

import com.coong_backend.domain.asset.dto.CategoryDto;
import com.coong_backend.domain.asset.entity.Category;
import com.coong_backend.domain.asset.repository.CategoryRepository;
import com.coong_backend.domain.asset.type.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> findAllIncomeCategories() {
        return findAllCategoriesByType(TransactionType.INCOME);
    }

    public List<CategoryDto> findAllExpenseCategories() {
        return findAllCategoriesByType(TransactionType.EXPENSE);
    }

    public List<CategoryDto> findAllCategoriesByType(TransactionType type) {
        return categoryRepository.findAllByCategoryType(type).stream()
                .map(c -> CategoryDto.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .categoryType(c.getCategoryType())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 거래 저장 시, transactionType + categoryId로 Category 엔티티를 조회합니다.
     * type과 실제 카테고리의 categoryType이 일치하는지도 함께 검증합니다.
     */
    public Category resolveCategory(TransactionType type, Long categoryId) {
        if (categoryId == null) return null;

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다: " + categoryId));

        if (category.getCategoryType() != type) {
            throw new IllegalArgumentException(
                    String.format("카테고리 타입 불일치: 요청 타입=%s, 카테고리 타입=%s", type, category.getCategoryType())
            );
        }

        return category;
    }
}