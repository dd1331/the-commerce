package com.example.thecommerce.user;

import com.example.thecommerce.common.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(GlobalExceptionHandler.class)
                .addFilters(new CharacterEncodingFilter("UTF-8", true)).build();
    }

    @Test
    public void join() throws Exception {
        JoinDto dto = JoinDto.builder().identifier("test").password("12345678").email("test@test.com").name("name").nickname("nickname").mobile("01000000000").build();

        mockMvc.perform(post("/api/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void joinDuplicated() throws Exception {
        JoinDto dto = JoinDto.builder().identifier("test").password("12345678").email("test@test.com").name("name").nickname("nickname").mobile("01000000000").build();

        doThrow(new DuplicateUserException()).when(userService).join(dto);

        mockMvc.perform(post("/api/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto))).
                andExpect(status().isConflict());
    }

    @Test
    void getUsers() throws Exception {
        Faker faker = new Faker();
        List<UserEntity> users = IntStream.range(0, 11)
                .mapToObj(i -> UserEntity.builder()
                        .identifier(faker.name().fullName())
                        .password(new Password(faker.internet().password()))
                        .email(faker.internet().emailAddress())
                        .name(faker.name().fullName())
                        .nickname(faker.name().username())
                        .mobile(faker.phoneNumber().cellPhone())
                        .build())
                .collect(Collectors.toList());


        Page<UserEntity> page = new PageImpl<>(users);
        Mockito.when(userService.getUsers(any(Pageable.class))).thenReturn(page);

        MvcResult mvcResult = mockMvc.perform(get("/api/user/list")
                        .param("page", "0")
                        .param("pageSize", "10")
                        .param("sortDirection", Sort.Direction.DESC.name())
                        .param("sortBy", SortBy.JOIN.name()))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();

        UserListResponse responseObject = objectMapper.readValue(content, UserListResponse.class);

        Assertions.assertEquals(11, responseObject.getUsers().size());
        Assertions.assertEquals(11, responseObject.getTotalElements());
        Assertions.assertEquals(1, responseObject.getTotalPages());
        System.out.println(responseObject);


    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
