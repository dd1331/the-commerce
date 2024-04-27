package com.example.thecommerce.user;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
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
    @DisplayName("유저목록 조회 페이징")
    void getUsers() {
        Faker faker = new Faker();

        IntStream.range(0, 11)
                .mapToObj(i -> JoinDto.builder()
                        .identifier(faker.name().fullName())
                        .password(faker.internet().password())
                        .email(faker.internet().emailAddress())
                        .name(faker.name().fullName())
                        .nickname(faker.name().username())
                        .mobile(faker.phoneNumber().cellPhone())
                        .build()).map(userService::join)
                .collect(Collectors.toList());


        Pageable pageable = PageRequest.of(0, 4, Sort.by("createdAt"));
        Pageable pageable2 = PageRequest.of(1, 4, Sort.by("createdAt"));
        Pageable pageable3 = PageRequest.of(2, 4, Sort.by("createdAt"));
        Page<UserEntity> page = userService.getUsers(pageable);
        Page<UserEntity> page2 = userService.getUsers(pageable2);
        Page<UserEntity> page3 = userService.getUsers(pageable3);

        assertEquals(page.getTotalElements(), 11);
        assertEquals(page.getTotalPages(), 3);
        assertEquals(page.getContent().size(), 4);
        assertEquals(page.getNumber(), 0);
        assertEquals(page.getSize(), 4);
        assertEquals(page.get().count(), 4);
        assertEquals(page2.getTotalElements(), 11);
        assertEquals(page2.getTotalPages(), 3);
        assertEquals(page2.getContent().size(), 4);
        assertEquals(page2.getNumber(), 1);
        assertEquals(page2.getSize(), 4);
        assertFalse(page2.isFirst());
        assertEquals(page3.getTotalElements(), 11);
        assertEquals(page3.getTotalPages(), 3);
        assertEquals(page3.getContent().size(), 3);
        assertEquals(page3.getNumber(), 2);
        assertEquals(page3.getSize(), 4);
        assertEquals(page3.get().count(), 3);
    }

    @Test
    @DisplayName("가입일로 정렬")
    void getUsersSortedByCreatedAt() {
        Faker faker = new Faker();
        List<UserEntity> users = IntStream.range(0, 11)
                .mapToObj(i -> JoinDto.builder()
                        .identifier(faker.name().fullName())
                        .password(faker.internet().password())
                        .email(faker.internet().emailAddress())
                        .name(faker.name().fullName())
                        .nickname(faker.name().username())
                        .mobile(faker.phoneNumber().cellPhone())
                        .build())
                .map(userService::join)
                .collect(Collectors.toList());

        entityManager.flush();
        entityManager.clear();

        List<UserEntity> expectedUsers = users.stream()
                .sorted(Comparator.comparing(UserEntity::getCreatedAt).reversed())
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<UserEntity> page = userService.getUsers(pageable);

        List<UserEntity> actualUsers = page.getContent();
        System.out.println(actualUsers);
        assertIterableEquals(expectedUsers, actualUsers);

        assertEquals(page.getTotalPages(), 1);
        assertEquals(page.getSize(), 20);


    }

    @Test
    @DisplayName("이름으로 정렬")
    void getUsersSortedByName() {
        Faker faker = new Faker();
        List<UserEntity> users = IntStream.range(0, 11)
                .mapToObj(i -> JoinDto.builder()
                        .identifier(faker.name().fullName())
                        .password(faker.internet().password())
                        .email(faker.internet().emailAddress())
                        .name(faker.name().fullName())
                        .nickname(faker.name().username())
                        .mobile(faker.phoneNumber().cellPhone())
                        .build())
                .map(userService::join)
                .collect(Collectors.toList());

        List<UserEntity> expectedUsers = users.stream()
                .sorted(Comparator.comparing(UserEntity::getName).reversed())
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "name"));
        Page<UserEntity> page = userService.getUsers(pageable);

        List<UserEntity> actualUsers = page.getContent();
        System.out.println(actualUsers);
        assertIterableEquals(expectedUsers, actualUsers);

        assertEquals(page.getTotalPages(), 1);
        assertEquals(page.getSize(), 20);


    }

    @Test()
    @DisplayName("유저의 변경 가능한 모든 데이터 변경")
    void updateUserAll() {
        Faker faker = new Faker();
        JoinDto dto = JoinDto.builder()
                .identifier(faker.name().fullName())
                .password(faker.internet().password())
                .email(faker.internet().emailAddress())
                .name(faker.name().fullName())
                .nickname(faker.name().username())
                .mobile(faker.phoneNumber().cellPhone()).build();

        UserEntity user = userService.join(dto);

        UpdateDto updateDto = UpdateDto.builder()
                .password(faker.internet().password())
                .email(faker.internet().emailAddress())
                .name(faker.name().fullName())
                .nickname(faker.name().username())
                .mobile(faker.phoneNumber().cellPhone()).build();

        userService.updateUser(user.getIdentifier(), updateDto);

        UserEntity updatedUser = entityManager.find(UserEntity.class, user.getId());

        assertEquals(updatedUser.getName(), updateDto.getName());
        assertEquals(updatedUser.getNickname(), updateDto.getNickname());
        assertEquals(updatedUser.getMobile(), updateDto.getMobile());
        assertEquals(updatedUser.getEmail(), updateDto.getEmail());
        assertTrue(updatedUser.getPassword().matches(updateDto.getPassword()));
    }

    @Test()
    @DisplayName("유저의 변경값이 없는 경우 기존 값 유지")
    void updateUserPartial() {
        Faker faker = new Faker();
        JoinDto dto = JoinDto.builder()
                .identifier(faker.name().fullName())
                .password(faker.internet().password())
                .email(faker.internet().emailAddress())
                .name(faker.name().fullName())
                .nickname(faker.name().username())
                .mobile(faker.phoneNumber().cellPhone()).build();

        UserEntity user = userService.join(dto);

        UpdateDto updateDto = UpdateDto.builder().build();
        userService.updateUser(user.getIdentifier(), updateDto);

        UserEntity updatedUser = entityManager.find(UserEntity.class, user.getId());


        assertEquals(user, updatedUser);
    }
}