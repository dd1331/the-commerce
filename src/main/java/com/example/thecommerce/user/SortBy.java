package com.example.thecommerce.user;

import lombok.Getter;

@Getter
// TODO: validation
public enum SortBy {
    JOIN("createdAt"),
    NAME("name");

    private final String value;

    SortBy(String value) {
        this.value = value;
    }

}