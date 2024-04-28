package com.example.thecommerce.user;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserListRequest {
    @Parameter(description = "페이지 번호", example = "0")
    @NotNull
    int page;

    @Parameter(description = "페이지 크기", example = "10")
    @NotNull
    int pageSize;

    @Parameter(description = "정렬 방향", example = "ASC")
    @NotNull(message = "ASC/DESC의 필수값이 필요합니다")
    Sort.Direction sortDirection;

    @Parameter(description = "정렬 기준", example = "NAME")
    @NotNull(message = "JOIN/NAME의 필수값이 필요합니다")
    SortBy sortBy;
}
