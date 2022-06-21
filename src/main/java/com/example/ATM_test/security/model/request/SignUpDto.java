package com.example.ATM_test.security.model.request;

import lombok.Data;

@Data
public class SignUpDto {

    private String cardNumber;

    private String pinCode;
}
