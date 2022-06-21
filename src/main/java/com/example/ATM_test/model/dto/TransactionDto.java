package com.example.ATM_test.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class TransactionDto {

    private Long id;

    private LocalDateTime localDateTime;

    private String transaction;

    private Double balance;
}
