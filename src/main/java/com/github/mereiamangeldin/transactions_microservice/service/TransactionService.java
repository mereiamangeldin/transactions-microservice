package com.github.mereiamangeldin.transactions_microservice.service;

import com.github.mereiamangeldin.transactions_microservice.dto.transactionsDTO.TransactionCreateDTO;
import com.github.mereiamangeldin.transactions_microservice.dto.transactionsDTO.TransactionDTO;
import com.github.mereiamangeldin.transactions_microservice.dto.transactionsDTO.TransactionExceededLimitDTO;
import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;
import com.github.mereiamangeldin.transactions_microservice.enums.ExpenseCategory;
import com.github.mereiamangeldin.transactions_microservice.model.Limit;
import com.github.mereiamangeldin.transactions_microservice.model.Transaction;
import com.github.mereiamangeldin.transactions_microservice.model.TransactionExceededLimit;
import com.github.mereiamangeldin.transactions_microservice.repository.TransactionExceededLimitRepository;
import com.github.mereiamangeldin.transactions_microservice.repository.TransactionRepository;
import com.github.mereiamangeldin.transactions_microservice.util.DateHelper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;
    private final TransactionExceededLimitRepository transactionExceededLimitRepository;
    private final ILimitService limitService;
    private final ICurrencyRateService currencyRateService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, TransactionExceededLimitRepository transactionExceededLimitRepository, ILimitService limitService, ICurrencyRateService currencyRateService) {
        this.transactionRepository = transactionRepository;
        this.transactionExceededLimitRepository = transactionExceededLimitRepository;
        this.limitService = limitService;
        this.currencyRateService = currencyRateService;
    }

    @Override
    public TransactionDTO createTransaction(TransactionCreateDTO transactionCreate) {
        logger.info("Creating transaction: {}", transactionCreate);

        logger.trace("Getting current currencies rate");
        float currentKZTtoUSDRate = currencyRateService.getCurrentRateByCurrencies(CurrencyShortname.KZT, CurrencyShortname.USD);
        float currentRUBtoUSDRate = currencyRateService.getCurrentRateByCurrencies(CurrencyShortname.RUB, CurrencyShortname.USD);
        TransactionDTO transaction = modelMapper.map(transactionCreate, TransactionDTO.class);
        LocalDateTime currentDate = LocalDateTime.now();
        transaction.setDatetime(currentDate);
        transaction.setMonthYear(DateHelper.getMonthYear(currentDate));
        switch (transaction.getCurrencyShortname()){
            case USD -> transaction.setUsdSum(transaction.getAmount());
            case RUB -> transaction.setUsdSum((float) (transaction.getAmount()*currentRUBtoUSDRate));
            case KZT -> transaction.setUsdSum((float) (transaction.getAmount()*currentKZTtoUSDRate));
        }
        logger.trace("Getting total expense by category: {}", transaction.getExpenseCategory());
        float totalExpenseByCategory = getTotalExpenseByCategoryOfCurrentMonth(transaction.getExpenseCategory()) + transaction.getUsdSum();
        Limit limitForExpenseCategory = limitService.getCurrentLimitByExpenseCategory(transaction.getExpenseCategory());

        transaction.setLimitExceeded(totalExpenseByCategory  > limitForExpenseCategory.getSum());
        Transaction tToSave = modelMapper.map(transaction, Transaction.class);
        logger.trace("Trying to save transaction to db: {}", tToSave);

        Transaction returnedTransaction = transactionRepository.save(tToSave);
        if(returnedTransaction.isLimitExceeded()){
            TransactionExceededLimit transactionExceededLimit = new TransactionExceededLimit();
            transactionExceededLimit.setTransaction(returnedTransaction);
            transactionExceededLimit.setLimit(limitForExpenseCategory);
            transactionExceededLimit.setExceededAmount(limitForExpenseCategory.getSum() - totalExpenseByCategory);
            transactionExceededLimitRepository.save(transactionExceededLimit);

        }
        TransactionDTO result = modelMapper.map(transactionRepository.save(tToSave), TransactionDTO.class);
        logger.info("Saving transaction: {}", result);
        return result;
    }

    @Override
    public List<TransactionDTO> getAllTransactions(String monthYear) {
        List<Transaction> transactions = transactionRepository.findAll();
        if (monthYear != null && !monthYear.trim().isEmpty()) {
            transactions = transactionRepository.findByMonthYear(monthYear);
        } else {
            transactions = transactionRepository.findAll(Sort.by(Sort.Order.desc("id")));
        }
        return transactions.stream().map(transaction -> modelMapper.map(transaction, TransactionDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<TransactionExceededLimitDTO> getTransactionsExceededLimit() {
        List<TransactionExceededLimit> transactions = transactionExceededLimitRepository.findAll(Sort.by(Sort.Order.desc("id")));
        return transactions.stream().map(transaction -> modelMapper.map(transaction, TransactionExceededLimitDTO.class)).collect(Collectors.toList());
    }

    float getTotalExpenseByCategoryOfCurrentMonth(ExpenseCategory expenseCategory) {
        LocalDateTime currentDate = LocalDateTime.now();
        String monthYear = DateHelper.getMonthYear(currentDate);
        Float result = transactionRepository.getTotalExpenseByCategoryOfCurrentMonth(monthYear, expenseCategory);
        if (result == null) {
            return 0f;
        }
        return result;
    }
}

