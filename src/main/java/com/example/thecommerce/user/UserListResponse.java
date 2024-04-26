package com.example.thecommerce.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class UserListResponse {
    private int totalPages;
    private long totalElements;
    private List<UserEntity> users;


}

