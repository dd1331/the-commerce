package com.example.thecommerce.user;

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
    private String identifier;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    private String name;

    @NotBlank
    @Mobile
    private String mobile;

    @NotBlank
    @Email
    private String email;
}
