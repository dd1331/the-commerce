package com.example.thecommerce.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class JoinDto {
    @NotBlank
    @Size(min = 5, max = 15)
    @Schema(example = "test2")
    private String identifier;

    @NotBlank
    @Size(min = 8, max = 20)
    @Schema(example = "123456789")
    private String password;

    @NotBlank
    @Size(min = 3, max = 15)
    @Schema(example = "닉네임")
    private String nickname;

    @NotBlank
    @Schema(example = "이름")
    private String name;

    @NotBlank
    @Schema(example = "01000000000")
    @Mobile
    private String mobile;

    @NotBlank
    @Email(message = "메일형식이 올바르지 않습니다")
    @Schema(example = "test@test.com")
    private String email;
}
