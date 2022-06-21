package com.example.ATM_test.service.impl;

import com.example.ATM_test.exceptions.AtmException;
import com.example.ATM_test.model.Transaction;
import com.example.ATM_test.model.User;
import com.example.ATM_test.model.dto.TransactionDto;
import com.example.ATM_test.repository.TransactionRepository;
import com.example.ATM_test.repository.UserRepository;
import com.example.ATM_test.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.ATM_test.constant.ErrorConstants.TRANSACTION_LIST_IS_EMPTY;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void setTransaction(User user, TransactionDto transactionDto) {

        Transaction transaction = new Transaction();
        transaction.setLocalDateTime(LocalDateTime.now());
        transaction.setTransaction(transactionDto.getTransaction());
        transaction.setBalance(user.getBalance());
        transaction.setUser(user);
        transactionRepository.save(transaction);

        List<Transaction> transactionList = user.getTransactions();
        transactionList.add(transaction);
        user.setTransactions(transactionList);
        userRepository.save(user);
    }

    @Override
    public List<TransactionDto> getAllTransaction(User user) {

        List<TransactionDto> transactionDtoList = new ArrayList<>();
        List<Transaction> transactionList = transactionRepository.findAllByUserId(user.getId());

        if (!transactionList.isEmpty()) {
            transactionList.stream().forEach(transaction -> {
                TransactionDto transactionDto = new TransactionDto();

                transactionDto.setId(transaction.getId());
                transactionDto.setLocalDateTime(transaction.getLocalDateTime());
                transactionDto.setTransaction(transaction.getTransaction());
                transactionDto.setBalance(transaction.getBalance());

                transactionDtoList.add(transactionDto);
            });
            return transactionDtoList;
        } else {
            throw new AtmException(TRANSACTION_LIST_IS_EMPTY);
        }
    }

}
