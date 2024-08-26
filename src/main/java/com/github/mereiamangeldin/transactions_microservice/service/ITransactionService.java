package com.github.mereiamangeldin.transactions_microservice.service;

import com.github.mereiamangeldin.transactions_microservice.dto.transactionsDTO.TransactionCreateDTO;
import com.github.mereiamangeldin.transactions_microservice.dto.transactionsDTO.TransactionDTO;
import com.github.mereiamangeldin.transactions_microservice.dto.transactionsDTO.TransactionExceededLimitDTO;
import com.github.mereiamangeldin.transactions_microservice.model.Transaction;

import java.util.List;

public interface ITransactionService {
    TransactionDTO createTransaction(TransactionCreateDTO transactioCreate);
    List<TransactionDTO> getAllTransactions(String monthYear);
    List<TransactionExceededLimitDTO> getTransactionsExceededLimit();

}
