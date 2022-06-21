package com.example.ATM_test.model.dto.outputDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RegisterUserOutputDto {

    private Long id;

    private String cardNumber;
}
