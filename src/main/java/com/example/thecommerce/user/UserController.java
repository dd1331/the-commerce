package com.example.thecommerce.user;

import org.springframework.web.bind.annotation.*;

@RestController(value = "/api/user")
public class UserController {

    UserService userService;

    @PostMapping("join")
    void join(@RequestBody JoinDto dto){
        userService.join(dto);

    }
    @GetMapping("list")
    void getUsers(@RequestBody ListDto dto){
        userService.getUsers(dto);

    }

    @PatchMapping("{identifier}")
    void updateUser(@PathVariable String identifier, @RequestBody UpdateDto dto){
        userService.updateUser(identifier, dto);
    }
}
