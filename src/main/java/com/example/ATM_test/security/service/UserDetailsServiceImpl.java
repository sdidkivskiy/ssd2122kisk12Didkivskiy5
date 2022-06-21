package com.example.ATM_test.security.service;

import com.example.ATM_test.model.User;
import com.example.ATM_test.model.dto.outputDto.RegisterUserOutputDto;
import com.example.ATM_test.repository.UserRepository;
import com.example.ATM_test.security.model.UserPrincipal;
import com.example.ATM_test.security.model.request.SignUpDto;
import com.example.ATM_test.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.example.ATM_test.constant.ErrorConstants.USER_BY_SUCH_CARDNUMBER_IS_NOT_FOUND;
import static com.example.ATM_test.model.Role.ROLE_USER;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String cardNumber) throws UsernameNotFoundException {

        User user = userRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new UsernameNotFoundException(USER_BY_SUCH_CARDNUMBER_IS_NOT_FOUND + cardNumber));

        return UserPrincipal.create(user);
    }

    public RegisterUserOutputDto create(SignUpDto signUpDto) {
        User user = new User();
        user.setCardNumber(signUpDto.getCardNumber());
        user.setPinCode(passwordEncoder.encode(signUpDto.getPinCode()));
        user.setRoleset(Collections.singleton(ROLE_USER));
        user.setBalance((double) 0);
        userRepository.save(user);

        RegisterUserOutputDto registerUserOutputDto = new RegisterUserOutputDto();
        registerUserOutputDto.setId(user.getId());
        registerUserOutputDto.setCardNumber(user.getCardNumber());

        return registerUserOutputDto;
    }
}
