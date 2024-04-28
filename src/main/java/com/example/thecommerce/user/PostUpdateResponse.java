package com.example.thecommerce.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostUpdateResponse {
    String name;
    String nickname;
    String email;
    //    String password;
    String mobile;
}
