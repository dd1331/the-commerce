package com.example.thecommerce.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testJoin_IdentifierExists() {
        JoinDto dto = new JoinDto();
        dto.setIdentifier("testIdentifier");

        when(userRepository.existsByIdentifier(dto.getIdentifier())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> userService.join(dto));
    }

    @Test
    void testJoin_IdentifierNotExists() {
        JoinDto dto = new JoinDto();
        dto.setIdentifier("testIdentifier");

        when(userRepository.existsByIdentifier(dto.getIdentifier())).thenReturn(false);

        assertDoesNotThrow(() -> userService.join(dto));
    }
}