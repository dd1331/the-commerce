package com.example.thecommerce.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // TODO: response dto
    public UserEntity join(JoinDto dto) {

        validateDuplication(dto);

        UserEntity user = UserEntity.builder().build();

        user.join(dto);

        userRepository.save(user);

        return user;
    }

    public void validateDuplication(JoinDto dto) {
        boolean email = userRepository.existsByEmail(dto.getEmail());

        if (email) throw new DuplicateUserException("이메일: " + dto.getEmail());

        boolean mobile = userRepository.existsByMobile(dto.getMobile());

        if (mobile) throw new DuplicateUserException("휴대폰: " + dto.getMobile());

        boolean identifier = userRepository.existsByIdentifier(dto.getIdentifier());

        if (identifier) throw new DuplicateUserException("아이디: " + dto.getIdentifier());

    }

    public Page<UserEntity> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public UserEntity updateUser(String identifier, UpdateDto dto) {

        Optional<UserEntity> user = userRepository.findOneByIdentifier(identifier);

        if (!user.isPresent()) throw new UserNotFoundException();

        user.get().update(dto);

        return userRepository.save(user.get());
    }
}
