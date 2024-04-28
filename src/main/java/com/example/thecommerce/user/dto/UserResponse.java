package com.example.thecommerce.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
    @Schema(example = "이름")
    String name;
    @Schema(example = "닉네임")
    String nickname;
    @Schema(example = "test@test.com")
    String email;
    @Schema(example = "01000000000")
    String mobile;
    @Schema(example = "test")
    private String identifier;
}
