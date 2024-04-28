package com.example.thecommerce.user;

import com.example.thecommerce.user.dto.JoinDto;
import com.example.thecommerce.user.exception.DuplicateUserException;
import com.example.thecommerce.user.repository.UserRepository;
import com.example.thecommerce.user.service.DefaultUserValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserValidationServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultUserValidationService validationService;


    @BeforeEach
    public void setUp() {

        when(userRepository.existsByMobile(any())).thenReturn(false);
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByIdentifier(any())).thenReturn(false);
    }

    @Test
    @DisplayName("존재하는 아이디일경우 예외")
    void identifierExists() {
        JoinDto dto = new JoinDto();

        when(userRepository.existsByIdentifier(any())).thenReturn(true);

        assertThrows(DuplicateUserException.class, () -> validationService.validateDuplication(dto));
    }

    @Test
    @DisplayName("아디디가 존재하지 않는경우 성공")
    void identifierNotExists() {
        JoinDto dto = new JoinDto();

        assertDoesNotThrow(() -> validationService.validateDuplication(dto));
    }

    @Test
    @DisplayName("번호가 존재하지 않는경우 성공")
    void mobileNotExists() {
        JoinDto dto = new JoinDto();

        assertDoesNotThrow(() -> validationService.validateDuplication(dto));
    }


    @Test
    @DisplayName("번호가 존재하는 경우 예외")
    void mobileExists() {
        JoinDto dto = new JoinDto();

        when(userRepository.existsByMobile(any())).thenReturn(true);

        assertThrows(DuplicateUserException.class, () -> validationService.validateDuplication(dto));
    }


    @Test
    @DisplayName("메일이 존재하지 않는경우 성공")
    void emailNotExists() {
        JoinDto dto = new JoinDto();

        assertDoesNotThrow(() -> validationService.validateDuplication(dto));
    }


    @Test
    @DisplayName("메일이 존재하는 경우 예외")
    void emailExists() {
        JoinDto dto = new JoinDto();

        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(DuplicateUserException.class, () -> validationService.validateDuplication(dto));
    }

}