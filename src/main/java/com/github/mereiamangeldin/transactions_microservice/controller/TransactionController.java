package com.github.mereiamangeldin.transactions_microservice.controller;

import com.github.mereiamangeldin.transactions_microservice.dto.transactionsDTO.TransactionCreateDTO;
import com.github.mereiamangeldin.transactions_microservice.dto.transactionsDTO.TransactionDTO;
import com.github.mereiamangeldin.transactions_microservice.dto.transactionsDTO.TransactionExceededLimitDTO;
import com.github.mereiamangeldin.transactions_microservice.model.TransactionExceededLimit;
import com.github.mereiamangeldin.transactions_microservice.service.ITransactionService;
import com.github.mereiamangeldin.transactions_microservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/transactions")
@RestController
public class TransactionController {
    private final ITransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping
    @Operation(
            summary = "Create a new transaction",
            description = "1. accountFrom and accountTo must be 10 digits account number\n2. expenseCategory options: [GOOD, SERVICE]\n3. currencyShortname options: [USD, KZT, RUB]"
    )
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionCreateDTO transactionCreate) {
        TransactionDTO transaction = transactionService.createTransaction(transactionCreate);
        return ResponseEntity.ok(transaction);
    }

    @Operation(
            summary = "Get all transactions",
            description = "By default will return transactions ordered by id in descending order.\nIf you will set month-year it return transactions according to this value"
    )
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getTransactions(@RequestParam(value = "monthYear", required = false) String monthYear) {
        List<TransactionDTO> transactions = transactionService.getAllTransactions(monthYear);
        return ResponseEntity.ok(transactions);
    }

    @Operation(
            summary = "Get transactions which exceeded limit",
            description = "Every object will consist which transaction exceeded which limit, also for how much it was exceeded"
    )
    @GetMapping("/exceeded-limit")
    public ResponseEntity<List<TransactionExceededLimitDTO>> getTransactionsExceededLimit() {
        List<TransactionExceededLimitDTO> transactions = transactionService.getTransactionsExceededLimit();
        return ResponseEntity.ok(transactions);
    }

}
