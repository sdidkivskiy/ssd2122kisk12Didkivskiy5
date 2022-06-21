package com.example.ATM_test.service.impl;

import com.example.ATM_test.exceptions.AtmException;
import com.example.ATM_test.exceptions.DataNotFoundException;
import com.example.ATM_test.model.User;
import com.example.ATM_test.model.dto.*;
import com.example.ATM_test.model.dto.outputDto.BalanceOutputDto;
import com.example.ATM_test.model.dto.outputDto.GetCashOutputDto;
import com.example.ATM_test.model.dto.outputDto.ReplenishmentOfBalanceAnotherUserOutputDto;
import com.example.ATM_test.model.dto.outputDto.ReplenishmentOfBalanceOutputDto;
import com.example.ATM_test.repository.UserRepository;
import com.example.ATM_test.service.TransactionService;
import com.example.ATM_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.ATM_test.constant.ErrorConstants.*;
import static com.example.ATM_test.constant.TransactionConstants.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TransactionService transactionService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.transactionService = transactionService;
    }

    @Override
    public User getByCardNumber(String cardNumber) {
        return userRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new DataNotFoundException(USER_BY_SUCH_CARDNUMBER_IS_NOT_FOUND));
    }

    @Transactional
    @Override
    public ReplenishmentOfBalanceOutputDto replenishmentOfBalance(ReplenishmentOfBalanceDto replenishmentOfBalanceDto) {
        User user = getCurrentUser();
        user.setBalance(user.getBalance() + replenishmentOfBalanceDto.getSum());

        userRepository.save(user);

        ReplenishmentOfBalanceOutputDto replenishmentOfBalanceOutputDto = new ReplenishmentOfBalanceOutputDto();
        replenishmentOfBalanceOutputDto.setCardNumber(getCurrentUser().getCardNumber());
        replenishmentOfBalanceOutputDto.setBalance(getCurrentUser().getBalance());

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransaction(REPLENISHMENT_OF_BALANCE_TRANSACTION);
        transactionService.setTransaction(user, transactionDto);

        return replenishmentOfBalanceOutputDto;
    }

    @Transactional
    @Override
    public ReplenishmentOfBalanceAnotherUserOutputDto replenishmentOfBalanceAnotherUser(ReplenishmentOfBalanceAnotherUserDto replenishmentOfBalanceAnotherUserDto) {

        User recipientUser = getByCardNumber(replenishmentOfBalanceAnotherUserDto.getRecipientCardNumber());

        if (getCurrentUser().getBalance() >= replenishmentOfBalanceAnotherUserDto.getSum() && replenishmentOfBalanceAnotherUserDto.getSum() != 0 && recipientUser != null) {
            User senderUser = getCurrentUser();
            senderUser.setBalance(senderUser.getBalance() - replenishmentOfBalanceAnotherUserDto.getSum());
            userRepository.save(senderUser);

            recipientUser.setBalance(recipientUser.getBalance() + replenishmentOfBalanceAnotherUserDto.getSum());
            userRepository.save(recipientUser);

            ReplenishmentOfBalanceAnotherUserOutputDto replenishmentOfBalanceAnotherUserOutputDto = new ReplenishmentOfBalanceAnotherUserOutputDto();
            replenishmentOfBalanceAnotherUserOutputDto.setSenderCardNumber(getCurrentUser().getCardNumber());
            replenishmentOfBalanceAnotherUserOutputDto.setBalance(getCurrentUser().getBalance());

            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setTransaction(DEBITING_OF_BALANCE_TRANSACTION);
            transactionService.setTransaction(senderUser, transactionDto);

            transactionDto.setTransaction(REPLENISHMENT_OF_BALANCE_TRANSACTION);
            transactionService.setTransaction(recipientUser, transactionDto);

            return replenishmentOfBalanceAnotherUserOutputDto;
        } else throw new AtmException(INCORRECT_AMOUNT);

    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String cardNumber;
        if (principal instanceof UserDetails) {
            cardNumber = ((UserDetails) principal).getUsername();
        } else {
            cardNumber = principal.toString();
        }

        return getByCardNumber(cardNumber);
    }

    @Override
    public BalanceOutputDto getBalance() {
        User user = getCurrentUser();
        BalanceOutputDto balanceOutputDto = new BalanceOutputDto();

        balanceOutputDto.setCardNumber(user.getCardNumber());
        balanceOutputDto.setBalance(user.getBalance());

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransaction(GET_BALANCE_TRANSACTION);
        transactionService.setTransaction(user, transactionDto);

        return balanceOutputDto;
    }

    @Transactional
    @Override
    public GetCashOutputDto getCash(GetCashDto getCashDto) {
        User user = getCurrentUser();
        GetCashOutputDto getCashOutputDto = new GetCashOutputDto();

        if (user.getBalance() >= getCashDto.getSum()) {

            user.setBalance(user.getBalance() - getCashDto.getSum());
            userRepository.save(user);

            getCashOutputDto.setCardNumber(user.getCardNumber());
            getCashOutputDto.setBalance(user.getBalance());

            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setTransaction(GET_CASH_TRANSACTION);
            transactionService.setTransaction(user, transactionDto);

            return getCashOutputDto;
        } else throw new AtmException(INCORRECT_AMOUNT);
    }
}
