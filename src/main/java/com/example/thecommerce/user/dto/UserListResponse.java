package com.example.thecommerce.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class UserListResponse {
    @Schema(example = "10")
    private int totalPages;

    @Schema(example = "100")
    private long totalElements;

    private List<UserResponse> users;


}

