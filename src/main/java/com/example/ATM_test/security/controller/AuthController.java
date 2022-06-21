package com.example.ATM_test.security.controller;

import com.example.ATM_test.exceptions.UserExistsException;
import com.example.ATM_test.model.dto.outputDto.RegisterUserOutputDto;
import com.example.ATM_test.repository.UserRepository;
import com.example.ATM_test.security.model.request.SignUpDto;
import com.example.ATM_test.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.example.ATM_test.constant.ErrorConstants.USER_BY_SUCH_CARD_NUMBER_ALREADY_EXISTS;
import static com.example.ATM_test.constant.UrlConstants.*;

@RestController
@RequestMapping(API_V1 + AUTH)
public class AuthController {

    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserDetailsServiceImpl userDetailsService, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @PostMapping(SIGN_UP_ENDPOINT)
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterUserOutputDto registerUser(@RequestBody SignUpDto signUpDto) {

        if (userRepository.existsByCardNumber(signUpDto.getCardNumber())) {
            throw new UserExistsException(USER_BY_SUCH_CARD_NUMBER_ALREADY_EXISTS);
        } else return userDetailsService.create(signUpDto);

    }

}
