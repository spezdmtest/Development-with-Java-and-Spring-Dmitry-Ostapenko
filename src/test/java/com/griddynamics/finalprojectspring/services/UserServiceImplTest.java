package com.griddynamics.finalprojectspring.services;

import com.griddynamics.finalprojectspring.config.UserIntegrationConfig;
import com.griddynamics.finalprojectspring.dto.UserDTO;
import com.griddynamics.finalprojectspring.entities.User;
import com.griddynamics.finalprojectspring.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.UUID;


class UserServiceImplTest {
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private UserIntegrationConfig userIntegrationConfig;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all test");
    }

    @BeforeEach
    void SetUpBeforeEach() {
        System.out.println("Before each test");
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        repository = Mockito.mock(UserRepository.class);
        userIntegrationConfig = Mockito.mock(UserIntegrationConfig.class);
        userService = new UserServiceImpl(repository, passwordEncoder, userIntegrationConfig);
    }

    @AfterEach
    void afterEach() {
        System.out.println("After each test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After all test");
    }

    @Test
    void checkFindByName() {
        //have
        String name = "my@email.com";
        User expectedUser = User.builder().id(1L).email(name).build();

        Mockito.when(repository.findFirstByEmail(Mockito.anyString())).thenReturn(expectedUser);

        //execute
        User actualUser = userService.findByName(name);

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void checkFindByNameExact() {
        //have
        String name = "my@email.com";
        User expectedUser = User.builder().id(1L).email(name).build();

        Mockito.when(repository.findFirstByEmail(Mockito.eq(name))).thenReturn(expectedUser);

        //execute
        User actualUser = userService.findByName(name);
        User rndUser = userService.findByName(UUID.randomUUID().toString());

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);
        Assertions.assertNull(rndUser);
    }

    @Test
    void checkSave() {
        //have
        UserDTO userDTO = UserDTO.builder()
                .email("spezdm@gmail.com")
                .password("password")
                .build();

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        //execute
        var result = userService.save(userDTO);

        //check
        Assertions.assertTrue(result);
        Mockito.verify(passwordEncoder).encode(Mockito.anyString());
        Mockito.verify(repository).save(Mockito.any());
    }
}


