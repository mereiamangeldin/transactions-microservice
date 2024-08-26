package com.github.mereiamangeldin.transactions_microservice.service;

import com.github.mereiamangeldin.transactions_microservice.dto.limitDTO.LimitCreateDTO;
import com.github.mereiamangeldin.transactions_microservice.dto.limitDTO.LimitDTO;
import com.github.mereiamangeldin.transactions_microservice.enums.CurrencyShortname;
import com.github.mereiamangeldin.transactions_microservice.enums.ExpenseCategory;
import com.github.mereiamangeldin.transactions_microservice.model.Limit;
import com.github.mereiamangeldin.transactions_microservice.repository.LimitRepository;
import com.github.mereiamangeldin.transactions_microservice.util.DateHelper;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LimitService implements ILimitService  {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyRateService.class);
    private final LimitRepository limitRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public LimitService(LimitRepository limitRepository) {
        this.limitRepository = limitRepository;
    }

    @PostConstruct
    public void init() {
        initMonth();
    }

//        @Scheduled(fixedRate = 15000)
    @Scheduled(cron = "0 0 0 1 * ?")
    public void scheduledInitMonth() {
        initMonth();
    }

    public void initMonth() {
        LocalDateTime currentDate = LocalDateTime.now();
        String monthYear = DateHelper.getMonthYear(currentDate);
        List<ExpenseCategory> expenseCategories = List.of(ExpenseCategory.values());
        for (ExpenseCategory expenseCategory : expenseCategories) {
            LimitDTO limit1 = new LimitDTO(1000, currentDate, CurrencyShortname.USD, expenseCategory, monthYear);
            LimitDTO result = modelMapper.map(limitRepository.save(modelMapper.map(limit1, Limit.class)), LimitDTO.class);
            logger.info("Limit for category " + expenseCategory + " for month-year: " + monthYear + " created:\n" + result);
        }
        LimitDTO limit1 = new LimitDTO();
    }


    @Override
    public LimitDTO createLimit(LimitCreateDTO limitCreate) {
        LimitDTO limit = modelMapper.map(limitCreate, LimitDTO.class);
        LocalDateTime currentDate = LocalDateTime.now();
        limit.setDatetime(currentDate);
        limit.setMonthYear(DateHelper.getMonthYear(currentDate));
        Limit lToSave = modelMapper.map(limit, Limit.class);
        LimitDTO result = modelMapper.map(limitRepository.save(lToSave), LimitDTO.class);
        return result;
    }

    @Override
    public List<LimitDTO> getAllLimits(String monthYear) {
        List<Limit> limits = limitRepository.findAll();
        if (monthYear != null && !monthYear.trim().isEmpty()) {
            limits = limitRepository.findByMonthYear(monthYear);
        } else {
            limits = limitRepository.findAll(Sort.by(Sort.Order.desc("id")));
        }
        return limits.stream().map(limit -> modelMapper.map(limit, LimitDTO.class)).collect(Collectors.toList());
    }

    public Limit getCurrentLimitByExpenseCategory(ExpenseCategory expenseCategory) {
        String monthYear = DateHelper.getMonthYear(LocalDateTime.now());
        return limitRepository.getCurrentLimitByExpenseCategory(monthYear, expenseCategory);
    }
}
