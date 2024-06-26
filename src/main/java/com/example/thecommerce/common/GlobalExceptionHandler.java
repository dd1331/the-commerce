package com.example.thecommerce.common;

import com.example.thecommerce.user.exception.DuplicateUserException;
import com.example.thecommerce.user.exception.UserNotFoundException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Object> handleDuplicateUserException(DuplicateUserException ex, WebRequest request) {

        logger.info("유저 중복 발생. 대상 = {}", ex.getMessage());

        ErrorResponse bodyOfResponse = new ErrorResponse("유저 중복 발생. 대상 = " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(bodyOfResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {

        logger.info("유저 없음. 대상 = {}", ex.getMessage());

        ErrorResponse bodyOfResponse = new ErrorResponse("유저 없음. 대상 = " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bodyOfResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        String bodyOfResponse = "유효성에러: " + ex.getBindingResult().getFieldError();

        // TODO: 대상코드??
        logger.info(bodyOfResponse);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bodyOfResponse);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        String bodyOfResponse = "An unexpected error occurred: " + ex.getMessage();

        logger.error("서버에러 발생. 메시지 = {}", ex.getMessage());
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bodyOfResponse);
    }

    @Getter
    private class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

    }
}
