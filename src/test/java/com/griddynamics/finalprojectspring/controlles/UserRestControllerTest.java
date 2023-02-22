package com.griddynamics.finalprojectspring.controlles;

import com.griddynamics.finalprojectspring.controllers.UserRestController;
import com.griddynamics.finalprojectspring.dto.UserDTO;
import com.griddynamics.finalprojectspring.services.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    private List<UserDTO> listOfUsers = new ArrayList<>();
    private UserDTO userDTO = new UserDTO(996L, "test@test.com", "password");

    @BeforeEach
    void setUp() {
        given(userService.findById(userDTO.getId())).willReturn(userDTO);
    }

    @Test
    void getUserAll() throws Exception {
        listOfUsers.add(UserDTO.builder().id(997L).email("test1@test.com").password("password").build());
        listOfUsers.add(UserDTO.builder().id(998L).email("test2@test.com").password("password").build());
        listOfUsers.add(UserDTO.builder().id(999L).email("test3@test.com").password("password").build());
        given(userService.findAll()).willReturn(listOfUsers);

        ResultActions response = mockMvc.perform(get("/api/v1/user/list", listOfUsers)
                .with(user("my@email.com").password("123")));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfUsers.size())));
    }

    @Test
    void getUserById() throws Exception {
        ResultActions response = mockMvc.perform(get("/api/v1/user/{id}", userDTO.getId())
                .with(user("my@email.com").password("123"))
                .accept(MediaType.APPLICATION_JSON));
        response.andDo(print());
        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(996)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("test@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("password")))
                .andDo(print());
    }
}
