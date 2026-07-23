package org.tournament.api;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.tournament.data.dto.UserDTO;
import org.tournament.data.enums.UserRole;
import org.tournament.endpoints.controllers.UserController;
import org.tournament.endpoints.filters.pageable.PageableFilter;
import org.tournament.endpoints.services.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

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
        verify(userService).getUserById(1);
    }

    @Test
    public void getUserById_ifUserNotExist_thenReturnNotFoundStatus() throws Exception{
        when(userService.getUserById(999))
                .thenThrow(new EntityNotFoundException("Not found user with id=999"));
        mockMvc.perform(get("/api/v1/user/999"))
                .andExpect(status().isNotFound());
        verify(userService).getUserById(999);
    }

    @Test
    public void getUserById_ifUserIdNotInteger_thenReturnBadRequestStatus() throws Exception{
        mockMvc.perform(get("/api/v1/user/abc"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(userService);
    }

    @Test
    public void getAllUserByFilter_ifExistTwoUsers_thenReturnListOfUserDTO() throws Exception{
        UserDTO user1 = new UserDTO(); user1.setEmail("CO@small.CK");
        UserDTO user2 = new UserDTO(); user2.setRole(UserRole.GUEST);
        when(userService.getAllUsers(any(PageableFilter.class)))
                .thenReturn(List.of(user1, user2));
        mockMvc.perform(get("/api/v1/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].email").value("CO@small.CK"))
                .andExpect(jsonPath("$[1].role").value("GUEST"));
        verify(userService).getAllUsers(any(PageableFilter.class));
    }

    @Test
    public void getAllUserByFilter_ifPaginationAllFilersNotNull_thenCheckPaginationFields() throws Exception{
        when(userService.getAllUsers(any(PageableFilter.class)))
                .thenReturn(List.of());
        mockMvc.perform(get("/api/v1/user/all")
                        .param("pageSize", "5")
                        .param("pageNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
        ArgumentCaptor<PageableFilter> filter = ArgumentCaptor.forClass(PageableFilter.class);
        verify(userService).getAllUsers(filter.capture());

        PageableFilter capturedFilter = filter.getValue();
        assertEquals(5, capturedFilter.pageSize());
        assertEquals(1, capturedFilter.pageNumber());
    }

    @Test
    public void getAllUserByFilter_nullPaginationFields_thenCheckNullEquals() throws Exception{
        when(userService.getAllUsers(any(PageableFilter.class)))
                .thenReturn(List.of());
        mockMvc.perform(get("/api/v1/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
        ArgumentCaptor<PageableFilter> filter = ArgumentCaptor.forClass(PageableFilter.class);
        verify(userService).getAllUsers(filter.capture());

        PageableFilter capturedFilter = filter.getValue();
        assertNull(capturedFilter.pageNumber());
        assertNull(capturedFilter.pageSize());
    }

    @Test
    public void getAllUserByFilter_invalidPaginationPageSize_thenThrowBadRequest() {
        assertThrows(ServletException.class, () ->
        mockMvc.perform(get("/api/v1/user/all")
                        .param("pageSize", "0"))
                .andExpect(status().isBadRequest()));
        verifyNoInteractions(userService);
    }

    @Test
    public void getAllUserByFilter_invalidPaginationPageNumber_thenThrowBadRequest() {
        assertThrows(ServletException.class, () ->
                mockMvc.perform(get("/api/v1/user/all")
                                .param("pageNumber", "-1"))
                        .andExpect(status().isBadRequest()));
        verifyNoInteractions(userService);
    }
}
