package com.example.ATM_test.service;

import com.example.ATM_test.model.User;
import com.example.ATM_test.model.dto.TransactionDto;

import java.util.List;

public interface TransactionService {

    void setTransaction(User user, TransactionDto transactionDto);

    List<TransactionDto> getAllTransaction (User user);
}
