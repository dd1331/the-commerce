package com.example.thecommerce.user.service;

import com.example.thecommerce.user.dto.JoinDto;

public interface UserValidationService {
    void validateDuplication(JoinDto dto);
}
