package com.example.thecommerce.user;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserValidationService validationService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // TODO: response dto
    public UserEntity join(JoinDto dto) {

        logger.info("유저 가입시작 DTO = {}", dto);
        validationService.validateDuplication(dto);

        UserEntity user = UserEntity.builder().build();

        user.join(dto);

        userRepository.save(user);

        logger.info("유저 가입성공 id = {}", dto.getIdentifier());

        return user;
    }


    public Page<UserEntity> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public UserEntity updateUser(String identifier, UpdateDto dto) {


        Optional<UserEntity> user = userRepository.findOneByIdentifier(identifier);


        if (!user.isPresent()) throw new UserNotFoundException("아이디 : " + identifier);

        logger.info("유저 수정시작 id = {}, 수정전 = {}, DTO = {}", identifier, user.get(), dto);

        user.get().update(dto);

        userRepository.save(user.get());

        logger.info("유저 수정성공 id = {}", identifier);

        return user.get();


    }
}
