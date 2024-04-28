package com.example.thecommerce.user.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@EqualsAndHashCode
public class Password {

    @Column(nullable = false)
    private String password;

    public Password(String rawPassword) {
        this.password = encryptPassword(rawPassword);
    }

    protected Password() {
    }

    public boolean matches(String rawPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, this.password);
    }

    private String encryptPassword(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }

}