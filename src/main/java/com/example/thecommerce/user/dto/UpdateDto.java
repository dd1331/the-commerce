package com.example.thecommerce.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@Getter
@ToString
@RequiredArgsConstructor
// TODO: validation
public class UpdateDto {

    @NotBlank
    @Schema(example = "이름")
    String name;

    @NotBlank
    @Size(min = 3, max = 15)
    @Schema(example = "닉네임")
    String nickname;

    @Schema(example = "test@test.com")
    @Email(message = "메일형식이 올바르지 않습니다")
    String email;

    @Size(min = 8, max = 20)
    @Schema(example = "123456789")
    String password;

    @Schema(example = "01000000000")
    @Mobile
    String mobile;
}
