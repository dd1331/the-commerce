package com.example.thecommerce.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // TODO: response dto
    public UserEntity join(JoinDto dto) {

        validateDuplication(dto);

        UserEntity user = UserEntity.builder()
                .identifier(dto.getIdentifier())
                .password(dto.getPassword())
                .name(dto.getName())
                .nickname(dto.getNickname())
                .mobile(dto.getMobile())
                .email(dto.getEmail())
                .build();

        userRepository.save(user);

        return user;
    }

    public void validateDuplication(JoinDto dto) {
        boolean email = userRepository.existsByEmail(dto.getEmail());
        System.out.println(userRepository.findAll());
        System.out.println(email + " " + dto.getEmail());

        if (email) throw new DuplicateUserException("이메일: " + dto.getEmail());

        boolean mobile = userRepository.existsByMobile(dto.getMobile());

        System.out.println(mobile + " " + dto.getMobile());

        if (mobile) throw new DuplicateUserException("휴대폰: " + dto.getMobile());

        boolean identifier = userRepository.existsByIdentifier(dto.getIdentifier());

        if (identifier) throw new DuplicateUserException("아이디: " + dto.getIdentifier());

    }

    public void getUsers(ListDto dto) {
    }

    public void updateUser(String identifier, UpdateDto dto) {
    }
}
