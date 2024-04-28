package com.example.thecommerce.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserValidationService implements UserValidationService {
    private final UserRepository userRepository;

    public void validateDuplication(JoinDto dto) {
        boolean email = userRepository.existsByEmail(dto.getEmail());

        if (email) throw new DuplicateUserException("이메일: " + dto.getEmail());

        boolean mobile = userRepository.existsByMobile(dto.getMobile());
    
        if (mobile) throw new DuplicateUserException("휴대폰: " + dto.getMobile());

        boolean identifier = userRepository.existsByIdentifier(dto.getIdentifier());

        if (identifier) throw new DuplicateUserException("아이디: " + dto.getIdentifier());

    }
}
