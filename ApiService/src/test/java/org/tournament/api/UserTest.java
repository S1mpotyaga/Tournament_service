package org.tournament.api;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.tournament.data.dto.UserDTO;
import org.tournament.data.enums.UserRole;
import org.tournament.endpoints.controllers.UserController;
import org.tournament.endpoints.filters.UserSearchFilter;
import org.tournament.endpoints.mappers.UserMapper;
import org.tournament.endpoints.services.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void getUserById_ifUserExist_thenReturnUserDTO() throws Exception{
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1); userDTO.setEmail("test@gmail.com"); userDTO.setNick("test");
        when(userService.getUserById(1)).thenReturn(userDTO);

        mockMvc.perform(get("/api/v1/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.nick").value("test"));
    }

    @Test
    public void getUserById_ifUserNotExist_thenReturnNotFoundStatus() throws Exception{
        when(userService.getUserById(999))
                .thenThrow(new EntityNotFoundException("Not found user with id=999"));
        mockMvc.perform(get("/api/v1/user/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getUserById_ifUserIdNotInteger_thenReturnBadRequestStatus() throws Exception{
        mockMvc.perform(get("/api/v1/user/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllUserByFilter_ifExistTwoUsers_thenReturnListOfUserDTO() throws Exception{
        UserDTO user1 = new UserDTO(); user1.setEmail("CO@small.CK");
        UserDTO user2 = new UserDTO(); user2.setRole(UserRole.GUEST);
        when(userService.searchAllByFilter(any(UserSearchFilter.class)))
                .thenReturn(List.of(user1, user2));
        mockMvc.perform(get("/api/v1/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].email").value("CO@small.CK"))
                .andExpect(jsonPath("$[1].role").value("GUEST"));
    }

    @Test
    public void getAllUserByFilter_ifPaginationAllFilersNotNull_thenCheckPaginationFields() throws Exception{
        when(userService.searchAllByFilter(any(UserSearchFilter.class)))
                .thenReturn(List.of());
        mockMvc.perform(get("/api/v1/user/all")
                        .param("userId", "2")
                        .param("pageSize", "5")
                        .param("pageNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
        ArgumentCaptor<UserSearchFilter> filter = ArgumentCaptor.forClass(UserSearchFilter.class);
        verify(userService).searchAllByFilter(filter.capture());

        UserSearchFilter capturedFilter = filter.getValue();
        assertEquals(2, capturedFilter.userId());
        assertEquals(5, capturedFilter.pageSize());
        assertEquals(1, capturedFilter.pageNumber());
    }

    @Test
    public void getAllUserByFilter_nullPaginationFields_thenCheckNullEquals() throws Exception{
        when(userService.searchAllByFilter(any(UserSearchFilter.class)))
                .thenReturn(List.of());
        mockMvc.perform(get("/api/v1/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
        ArgumentCaptor<UserSearchFilter> filter = ArgumentCaptor.forClass(UserSearchFilter.class);
        verify(userService).searchAllByFilter(filter.capture());

        UserSearchFilter capturedFilter = filter.getValue();
        assertNull(capturedFilter.userId());
        assertNull(capturedFilter.pageNumber());
        assertNull(capturedFilter.pageSize());
    }

    @Test
    public void getAllUserByFilter_invalidPaginationUserId_thenThrowBadRequest() throws Exception{
        mockMvc.perform(get("/api/v1/user/all")
                        .param("userId", "-2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllUserByFilter_invalidPaginationPageSize_thenThrowBadRequest() throws Exception{
        mockMvc.perform(get("/api/v1/user/all")
                        .param("pageSize", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllUserByFilter_invalidPaginationPageNumber_thenThrowBadRequest() throws Exception{
        mockMvc.perform(get("/api/v1/user/all")
                        .param("pageNumber", "-1"))
                .andExpect(status().isBadRequest());
    }
}
