package com.example.thecommerce.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
// TODO: validation
public class UpdateDto {
    String name;
    String nickname;
    String email;
    String password;
    String mobile;
}
