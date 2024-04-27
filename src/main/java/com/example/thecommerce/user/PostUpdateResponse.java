package com.example.thecommerce.user;

import lombok.Builder;

@Builder
public class PostUpdateResponse {
    String name;
    String nickname;
    String email;
    String password;
    String mobile;
}
