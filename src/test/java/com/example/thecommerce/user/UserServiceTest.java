package com.example.thecommerce.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @PersistenceContext
    private EntityManager entityManager;


    @Test
    void join() {
        JoinDto dto = JoinDto.builder().identifier("test").password("12345678").email("test@test.com").name("name").nickname("nickname").mobile("01000000000").build();

        UserEntity user = userService.join(dto);

        assertEquals(user.getIdentifier(), dto.getIdentifier());

    }

    @Test
    void joinDuplicatedIdException() {
        JoinDto dto = JoinDto.builder().identifier("test").password("12345678").email("test@test.com").name("name").nickname("nickname").mobile("01000000000").build();

        userService.join(dto);

        JoinDto newDto = JoinDto.builder().identifier("test").password("12345678").email("test@test.com2").name("name").nickname("nickname").mobile("01000000002").build();

        assertThrows(DuplicateUserException.class, () -> {
            userService.join(newDto);
        });

    }

    @Test
    void joinDuplicatedEmailException() {
        JoinDto dto = JoinDto.builder().identifier("test").password("12345678").email("test@test.com").name("name").nickname("nickname").mobile("01000000000").build();

        userService.join(dto);

        JoinDto newDto = JoinDto.builder().identifier("test2").password("12345678").email("test@test.com").name("name").nickname("nickname").mobile("01000000002").build();
        assertThrows(DuplicateUserException.class, () -> {
            userService.join(newDto);
        });
    }

    @Test
    void joinDuplicatedMobileException() {
        JoinDto dto = JoinDto.builder().identifier("test").password("12345678").email("test@test.com").name("name").nickname("nickname").mobile("01000000000").build();

        userService.join(dto);

        JoinDto newDto = JoinDto.builder().identifier("test2").password("12345678").email("test@test.com2").name("name").nickname("nickname").mobile("01000000000").build();
        assertThrows(DuplicateUserException.class, () -> {
            userService.join(newDto);
        });
    }

    @Test
    void getUsers() {
    }

    @Test
    void updateUser() {
    }
}