package com.example.ATM_test.model.dto.outputDto;

import com.example.ATM_test.model.Transaction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class TransactionOutputDto {

    private String cardNumber;

    private List<Transaction> transaction;
}
