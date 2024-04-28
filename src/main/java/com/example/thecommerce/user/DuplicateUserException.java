package com.example.thecommerce.user;

public class DuplicateUserException extends RuntimeException {

//    public DuplicateUserException() {
//        super();
//    }

    public DuplicateUserException(String message) {
        super(message);
    }
}
