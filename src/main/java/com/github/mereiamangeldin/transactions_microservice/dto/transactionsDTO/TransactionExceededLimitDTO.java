package com.github.mereiamangeldin.transactions_microservice.dto.transactionsDTO;

import com.github.mereiamangeldin.transactions_microservice.model.Limit;
import com.github.mereiamangeldin.transactions_microservice.model.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionExceededLimitDTO {
    private Long id;
    private Transaction transaction;
    private Limit limit;
    private float exceededAmount;
}
