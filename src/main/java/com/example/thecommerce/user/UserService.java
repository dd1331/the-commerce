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
    private final UserValidationService validationService;

    // TODO: response dto
    public UserEntity join(JoinDto dto) {

        validationService.validateDuplication(dto);

        UserEntity user = UserEntity.builder().build();

        user.join(dto);

        userRepository.save(user);

        return user;
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
