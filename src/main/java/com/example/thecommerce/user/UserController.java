package com.example.thecommerce.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/user")
public class UserController {

    UserService userService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    void join(@RequestBody JoinDto dto) {
        userService.join(dto);
    }

    @GetMapping("/list")
    UserListResponse getUsers(@RequestParam int page,
                              @RequestParam int pageSize,
                              @RequestParam Sort.Direction sortDirection,
                              @RequestParam SortBy sortBy) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortDirection, sortBy.getValue()));

        Page<UserEntity> usersPage = userService.getUsers(pageable);

        return UserListResponse.builder().users(usersPage.getContent()).totalPages(usersPage.getTotalPages()).totalElements(usersPage.getTotalElements()).build();

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
