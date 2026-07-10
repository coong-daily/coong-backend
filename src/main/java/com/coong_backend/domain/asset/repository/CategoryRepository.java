package com.coong_backend.domain.asset.repository;

import com.coong_backend.domain.asset.entity.Account;
import com.coong_backend.domain.asset.entity.Category;
import com.coong_backend.domain.asset.type.AccountStatus;
import com.coong_backend.domain.asset.type.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByCategoryType(TransactionType categoryType);
}