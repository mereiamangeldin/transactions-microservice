package com.github.mereiamangeldin.transactions_microservice.controller;

import com.github.mereiamangeldin.transactions_microservice.dto.limitDTO.LimitCreateDTO;
import com.github.mereiamangeldin.transactions_microservice.dto.limitDTO.LimitDTO;
import com.github.mereiamangeldin.transactions_microservice.service.ILimitService;
import com.github.mereiamangeldin.transactions_microservice.service.LimitService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/limits")
@RestController
public class LimitController {
    private final ILimitService limitService;

    @Autowired
    public LimitController(LimitService limitService) {
        this.limitService = limitService;
    }

    @Operation(
            summary = "Create a new limit",
            description = "1. expenseCategory options: [GOOD, SERVICE]\n2. currencyShortname options: [USD, KZT, RUB]"
    )
    @PostMapping
    public ResponseEntity<LimitDTO> createLimit(@Valid @RequestBody LimitCreateDTO limitCreate) {
        LimitDTO limit = limitService.createLimit(limitCreate);
        return ResponseEntity.ok(limit);
    }

    @Operation(
            summary = "Get all limits",
            description = "By default will return limits ordered by id in descending order.\nIf you will set month-year it return limits according to this value"
    )
    @GetMapping
    public ResponseEntity<List<LimitDTO>> getLimits(@RequestParam(value = "monthYear", required = false) String monthYear) {
        List<LimitDTO> limits = limitService.getAllLimits(monthYear);
        return ResponseEntity.ok(limits);
    }
}
