package com.example.thecommerce.user;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@ToString
@RequiredArgsConstructor
// TODO: validation
public class UpdateDto {
    String name;
    String nickname;
    String email;
    String password;
    String mobile;
}
