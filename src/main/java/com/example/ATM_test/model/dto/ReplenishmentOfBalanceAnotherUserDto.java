package com.example.ATM_test.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReplenishmentOfBalanceAnotherUserDto {

    private String recipientCardNumber;

    private Double sum;

}
