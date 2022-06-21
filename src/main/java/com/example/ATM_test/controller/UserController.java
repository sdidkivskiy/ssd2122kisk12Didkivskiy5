package com.example.ATM_test.controller;

import com.example.ATM_test.model.dto.*;
import com.example.ATM_test.model.dto.outputDto.BalanceOutputDto;
import com.example.ATM_test.model.dto.outputDto.GetCashOutputDto;
import com.example.ATM_test.model.dto.outputDto.ReplenishmentOfBalanceAnotherUserOutputDto;
import com.example.ATM_test.model.dto.outputDto.ReplenishmentOfBalanceOutputDto;
import com.example.ATM_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.ATM_test.constant.UrlConstants.*;

@RestController
@RequestMapping(API_V1 + USER)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(BALANCE_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    public BalanceOutputDto getBalance() {
        return userService.getBalance();
    }

    @PostMapping(REPLENISHMENT_OF_BALANCE_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    public ReplenishmentOfBalanceOutputDto replenishmentOfBalance(@RequestBody ReplenishmentOfBalanceDto replenishmentOfBalanceDto) {
        return userService.replenishmentOfBalance(replenishmentOfBalanceDto);
    }

    @PostMapping(REPLENISHMENT_OF_BALANCE_ANOTHER_USER_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    public ReplenishmentOfBalanceAnotherUserOutputDto replenishmentOfBalanceAnotherUser(@RequestBody ReplenishmentOfBalanceAnotherUserDto replenishmentOfBalanceAnotherUserDto) {
        return userService.replenishmentOfBalanceAnotherUser(replenishmentOfBalanceAnotherUserDto);
    }

    @PostMapping(GET_CASH_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    public GetCashOutputDto getCash(@RequestBody GetCashDto getCashDto) {
        return userService.getCash(getCashDto);
    }
}
