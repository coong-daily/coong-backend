package com.coong_backend.domain.asset.config;

import com.coong_backend.domain.asset.entity.*;
import com.coong_backend.domain.asset.repository.*;
import com.coong_backend.domain.asset.type.AccountStatus;
import com.coong_backend.domain.asset.type.AccountType;
import com.coong_backend.domain.asset.type.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AssetDataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final CategoryRepository categoryRepository;
    private final AssetRepository assetRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (accountRepository.count() > 0) {
            log.info("이미 목데이터가 존재합니다. 초기화를 건너뜁니다.");
            return;
        }

        log.info("목데이터 초기화를 시작합니다.");

        initCategories();
        initAccountsAndCards();
        initAssets();

        log.info("목데이터 초기화가 완료되었습니다.");
    }

    private void initCategories() {
        categoryRepository.save(Category.builder().name("급여").categoryType(TransactionType.INCOME).build());
        categoryRepository.save(Category.builder().name("부수입").categoryType(TransactionType.INCOME).build());
        categoryRepository.save(Category.builder().name("보너스").categoryType(TransactionType.INCOME).build());
        categoryRepository.save(Category.builder().name("이자").categoryType(TransactionType.INCOME).build());
        categoryRepository.save(Category.builder().name("기타").categoryType(TransactionType.INCOME).build());

        categoryRepository.save(Category.builder().name("쇼핑").categoryType(TransactionType.EXPENSE).build());
        categoryRepository.save(Category.builder().name("식비").categoryType(TransactionType.EXPENSE).build());
        categoryRepository.save(Category.builder().name("교통").categoryType(TransactionType.EXPENSE).build());
        categoryRepository.save(Category.builder().name("구독").categoryType(TransactionType.EXPENSE).build());
        categoryRepository.save(Category.builder().name("운동").categoryType(TransactionType.EXPENSE).build());
        categoryRepository.save(Category.builder().name("기타").categoryType(TransactionType.EXPENSE).build());

        log.info("Category 목데이터 생성 완료");
    }

    private void initAccountsAndCards() {
        // 계좌 1: 급여 통장 (입출금, 카드 2개)
        Account salaryAccount = accountRepository.save(Account.builder()
                .accountNumber("110-123-456789")
                .accountName("급여통장")
                .balance(3500000)
                .bank("국민은행")
                .accountType(AccountType.CHECKING)
                .expiryDate(null)
                .currency("KRW")
                .status(AccountStatus.ACTIVE)
                .memo("주거래 계좌")
                .build());

        cardRepository.save(Card.builder()
                .cardNumber("1234-5678-9012-3456")
                .cardName("국민 노리체크카드")
                .expiryDate(LocalDate.of(2028, 6, 30))
                .benefitCondition(300000)
                .cardCompany("KB국민카드")
                .paymentDay(LocalDate.of(2026, 7, 25))
                .account(salaryAccount)
                .build());

        cardRepository.save(Card.builder()
                .cardNumber("2345-6789-0123-4567")
                .cardName("국민 탄탄대로 신용카드")
                .expiryDate(LocalDate.of(2027, 12, 31))
                .benefitCondition(500000)
                .cardCompany("KB국민카드")
                .paymentDay(LocalDate.of(2026, 7, 14))
                .account(salaryAccount)
                .build());

        // 계좌 2: 비상금 저축 계좌 (카드 없음)
        accountRepository.save(Account.builder()
                .accountNumber("352-1234-5678-90")
                .accountName("비상금통장")
                .balance(8200000)
                .bank("카카오뱅크")
                .accountType(AccountType.EMERGENCY)
                .expiryDate(null)
                .currency("KRW")
                .status(AccountStatus.ACTIVE)
                .memo("비상금 용도, 출금 자제")
                .build());

        // 계좌 3: 저축 계좌
        accountRepository.save(Account.builder()
                .accountNumber("100-987-654321")
                .accountName("적금통장")
                .balance(5000000)
                .bank("신한은행")
                .accountType(AccountType.SAVINGS)
                .expiryDate(LocalDate.of(2027, 3, 1))
                .currency("KRW")
                .status(AccountStatus.ACTIVE)
                .memo("36개월 만기 적금")
                .build());

        // 계좌 4: 투자 계좌 (신용카드 결제용 겸용)
        Account investAccount = accountRepository.save(Account.builder()
                .accountNumber("523-1122-3344-55")
                .accountName("주식투자계좌")
                .balance(1200000)
                .bank("토스뱅크")
                .accountType(AccountType.INVESTMENT)
                .expiryDate(null)
                .currency("KRW")
                .status(AccountStatus.ACTIVE)
                .memo(null)
                .build());

        cardRepository.save(Card.builder()
                .cardNumber("3456-7890-1234-5678")
                .cardName("토스 신용카드")
                .expiryDate(LocalDate.of(2029, 3, 31))
                .benefitCondition(700000)
                .cardCompany("토스뱅크")
                .paymentDay(LocalDate.of(2026, 7, 5))
                .account(investAccount)
                .build());

        // 계좌 5: 연금 계좌
        accountRepository.save(Account.builder()
                .accountNumber("789-000-111222")
                .accountName("연금저축계좌")
                .balance(15000000)
                .bank("IBK기업은행")
                .accountType(AccountType.PENSION)
                .expiryDate(null)
                .currency("KRW")
                .status(AccountStatus.ACTIVE)
                .memo("55세 이후 수령 예정")
                .build());

        log.info("Account, Card 목데이터 생성 완료");
    }

    private void initAssets() {
        assetRepository.save(Asset.builder().name("입출금").amount(3500000).build());
        assetRepository.save(Asset.builder().name("저축").amount(13200000).build());
        assetRepository.save(Asset.builder().name("투자").amount(1200000).build());
        assetRepository.save(Asset.builder().name("비상금").amount(8200000).build());

        log.info("Asset 목데이터 생성 완료");
    }
}