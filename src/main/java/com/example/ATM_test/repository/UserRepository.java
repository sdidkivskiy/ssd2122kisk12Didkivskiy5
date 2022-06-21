package com.example.ATM_test.repository;

import com.example.ATM_test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByCardNumber(String cardNumber);

    Optional<User> findByCardNumber(String cardNumber);

    Optional<User> findById(Long id);
}
