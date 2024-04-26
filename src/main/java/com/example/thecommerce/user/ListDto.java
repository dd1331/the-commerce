package com.example.thecommerce.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@Builder
@Getter
//  TODO: validation
public class ListDto {
    int page;

    int pageSize;

    SortBy sortBy;

    Sort.Direction sortDirection;
}
