package com.example.ATM_test.security.model;

import com.example.ATM_test.model.Role;
import com.example.ATM_test.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class UserPrincipal implements UserDetails {

    private Long id;

    private String cardNumber;

    private String pinCode;

    private Set<Role> roleset;

    Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(User user) {

        List<GrantedAuthority> authorities = user.getRoleset().stream().map(role ->
                new SimpleGrantedAuthority(role.name())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getCardNumber(),
                user.getPinCode(),
                user.getRoleset(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    public String getUsername() {
        return cardNumber;
    }

    public String getPassword() {
        return pinCode;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}