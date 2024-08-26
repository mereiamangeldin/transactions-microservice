package com.github.mereiamangeldin.transactions_microservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "transaction_exceeded_limits", indexes = @Index(name = "idx_transaction_id", columnList = "transaction_id"))
public class TransactionExceededLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "limit_id", nullable = false)
    private Limit limit;

    private float exceededAmount;
}