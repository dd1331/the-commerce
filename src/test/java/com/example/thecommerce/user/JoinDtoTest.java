package com.example.thecommerce.user;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JoinDtoTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void testJoinDtoValidation_Valid() {
        JoinDto joinDto = new JoinDto();
        joinDto.setIdentifier("username");
        joinDto.setPassword("password");
        joinDto.setNickname("nickname");
        joinDto.setName("name");
        joinDto.setMobile("01000000000");
        joinDto.setEmail("test@example.com");

        Set<ConstraintViolation<JoinDto>> violations = validator.validate(joinDto);

        System.out.println(violations.size());


        assertTrue(violations.isEmpty());
    }

    @Test
    void testJoinDtoValidation_Invalid() {
        JoinDto joinDto = new JoinDto();
        joinDto.setIdentifier("");
        joinDto.setPassword(""); // 2
        joinDto.setNickname("");
        joinDto.setName("");
        joinDto.setMobile(""); // 2
        joinDto.setEmail("");


        Set<ConstraintViolation<JoinDto>> violations = validator.validate(joinDto);

        System.out.println(violations);

        assertEquals(10, violations.size(), "Invalid JoinDto should have 6 violations");
    }


}