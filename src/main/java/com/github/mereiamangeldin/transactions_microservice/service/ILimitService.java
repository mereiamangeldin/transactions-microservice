package com.github.mereiamangeldin.transactions_microservice.service;

import com.github.mereiamangeldin.transactions_microservice.dto.limitDTO.LimitCreateDTO;
import com.github.mereiamangeldin.transactions_microservice.dto.limitDTO.LimitDTO;
import com.github.mereiamangeldin.transactions_microservice.enums.ExpenseCategory;
import com.github.mereiamangeldin.transactions_microservice.model.Limit;

import java.util.List;

public interface ILimitService {
    LimitDTO createLimit(LimitCreateDTO limitCreate);
    List<LimitDTO> getAllLimits(String monthYear);
    Limit getCurrentLimitByExpenseCategory(ExpenseCategory expenseCategory);
}
