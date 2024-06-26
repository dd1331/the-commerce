package com.example.thecommerce.user.controller;

import com.example.thecommerce.user.dto.*;
import com.example.thecommerce.user.entity.UserEntity;
import com.example.thecommerce.user.exception.UserNotFoundException;
import com.example.thecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/user")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    void join(@Valid @RequestBody JoinDto dto) {
        userService.join(dto);
    }

    @GetMapping("/list")
    UserListResponse getUsers(@Valid @ModelAttribute UserListRequest dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getPageSize(), Sort.by(dto.getSortDirection(), dto.getSortBy().getValue()));

        Page<UserEntity> usersPage = userService.getUsers(pageable);

        return UserListResponse.builder().users(usersPage.getContent().stream()
                .map(user -> UserResponse.builder().name(user.getName())
                        .nickname(user.getNickname())
                        .mobile(user.getMobile())
                        .identifier(user.getIdentifier())
                        .email(user.getEmail()).build())
                .collect(Collectors.toList())).totalPages(usersPage.getTotalPages()).totalElements(usersPage.getTotalElements()).build();

    }


    @PatchMapping("{identifier}")
    PostUpdateResponse updateUser(@PathVariable String identifier, @RequestBody UpdateDto dto) throws UserNotFoundException {
        UserEntity user = userService.updateUser(identifier, dto);
        return PostUpdateResponse.builder()
                .email(user.getEmail())
                .mobile(user.getMobile())
                .name(user.getName())
                .nickname(user.getNickname())
                .build();
    }
}
