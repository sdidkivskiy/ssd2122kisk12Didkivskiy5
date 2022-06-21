package com.example.ATM_test.controller;

import com.example.ATM_test.model.dto.TransactionDto;
import com.example.ATM_test.service.TransactionService;
import com.example.ATM_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.ATM_test.constant.UrlConstants.*;

@RestController
@RequestMapping(API_V1 + TRANSACTION)
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDto> getAllTransaction() {
        return transactionService.getAllTransaction(userService.getCurrentUser());
    }
}
