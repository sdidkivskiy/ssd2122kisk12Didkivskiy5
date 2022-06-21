package com.example.ATM_test.service;

import com.example.ATM_test.model.User;
import com.example.ATM_test.model.dto.*;
import com.example.ATM_test.model.dto.outputDto.BalanceOutputDto;
import com.example.ATM_test.model.dto.outputDto.GetCashOutputDto;
import com.example.ATM_test.model.dto.outputDto.ReplenishmentOfBalanceAnotherUserOutputDto;
import com.example.ATM_test.model.dto.outputDto.ReplenishmentOfBalanceOutputDto;

public interface UserService {

    User getByCardNumber(String cardNumber);

    ReplenishmentOfBalanceOutputDto replenishmentOfBalance(ReplenishmentOfBalanceDto replenishmentOfBalanceDto);

    ReplenishmentOfBalanceAnotherUserOutputDto replenishmentOfBalanceAnotherUser(ReplenishmentOfBalanceAnotherUserDto replenishmentOfBalanceAnotherUserDto);

    User getCurrentUser();

    BalanceOutputDto getBalance();

    GetCashOutputDto getCash(GetCashDto getCashDto);

}
