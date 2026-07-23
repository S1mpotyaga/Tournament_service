package org.tournament.api;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.tournament.data.dto.MatchDTO;
import org.tournament.data.dto.UserDTO;
import org.tournament.endpoints.controllers.TournamentParticipantController;
import org.tournament.endpoints.filters.pageable.IdPaginationFilter;
import org.tournament.endpoints.services.MatchService;
import org.tournament.endpoints.services.TournamentParticipantService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TournamentParticipantController.class)
@AutoConfigureMockMvc
public class TournamentParticipantControllerTest {
    @MockBean
    private TournamentParticipantService tpService;
    @MockBean
    private MatchService matchService;
    @Autowired
    private MockMvc mockMvc;

    // api/v1/participant/user

    @Test
    public void getAllMatchesByUserIdByFilter_withParams_thenReturnAllUsersDTO()
            throws Exception
    {
        MatchDTO match1 = new MatchDTO(); MatchDTO match2 = new MatchDTO();
        when(matchService.findMatchesByUser(any(IdPaginationFilter.class)))
                .thenReturn(List.of(match1, match2));
        mockMvc.perform(get("/api/v1/participants/user/1")
                        .param("pageSize", "10")
                        .param("pageNumber", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        ArgumentCaptor<IdPaginationFilter> filter = ArgumentCaptor.forClass(IdPaginationFilter.class);
        verify(matchService).findMatchesByUser(filter.capture());
        IdPaginationFilter cFilter = filter.getValue();
        assertEquals(1, cFilter.Id());
        assertEquals(10, cFilter.pageSize());
        assertEquals(5, cFilter.pageNumber());
    }

    @Test
    public void getAllMatchesByUserIdByFilter_withOutParams_thenReturnAllUsersDTO()
            throws Exception
    {
        MatchDTO match1 = new MatchDTO(); MatchDTO match2 = new MatchDTO();
        when(matchService.findMatchesByUser(any(IdPaginationFilter.class)))
                .thenReturn(List.of(match1, match2));
        mockMvc.perform(get("/api/v1/participants/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        ArgumentCaptor<IdPaginationFilter> filter = ArgumentCaptor.forClass(IdPaginationFilter.class);
        verify(matchService).findMatchesByUser(filter.capture());
        IdPaginationFilter cFilter = filter.getValue();
        assertNull(cFilter.pageSize());
        assertNull(cFilter.pageNumber());
    }

    @Test
    public void getAllMatchesByUserIdByFilter_invalidPath_thenReturnBadRequestStatus()
            throws Exception
    {
        mockMvc.perform(get("/api/v1/participants/user/abc"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(matchService);

    }

    @Test
    public void getAllMatchesByUserIdByFilter_invalidParams_thenReturnBadRequestStatus()
            throws Exception
    {
        mockMvc.perform(get("/api/v1/participants/user/1")
                        .param("tournamentId", "0")
                        .param("pageSize", "-2")
                        .param("pageNumber", "abc"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(matchService);
    }

    // api/v1/participant/tournament

    @Test
    public void getAllUsersByTournamentIdByFilter_withParams_thenReturnAllUsersDTO()
            throws Exception
    {
        UserDTO user1 = new UserDTO(); UserDTO user2 = new UserDTO();
        when(tpService.getAllUsersByTournamentIdByFilter(any(IdPaginationFilter.class)))
                .thenReturn(List.of(user1, user2));
        mockMvc.perform(get("/api/v1/participants/tournament/1")
                .param("pageSize", "1")
                .param("pageNumber", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        ArgumentCaptor<IdPaginationFilter> filter = ArgumentCaptor.forClass(IdPaginationFilter.class);
        verify(tpService).getAllUsersByTournamentIdByFilter(filter.capture());
        IdPaginationFilter cFilter = filter.getValue();
        assertEquals(1, cFilter.pageSize());
        assertEquals(2, cFilter.pageNumber());
    }

    @Test
    public void getAllUsersByTournamentIdByFilter_withOutParams_thenReturnAllUsersDTO()
        throws Exception
    {
        UserDTO user1 = new UserDTO(); UserDTO user2 = new UserDTO();
        when(tpService.getAllUsersByTournamentIdByFilter(any(IdPaginationFilter.class)))
                .thenReturn(List.of(user1, user2));
        mockMvc.perform(get("/api/v1/participants/tournament/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        ArgumentCaptor<IdPaginationFilter> filter = ArgumentCaptor.forClass(IdPaginationFilter.class);
        verify(tpService).getAllUsersByTournamentIdByFilter(filter.capture());
        IdPaginationFilter cFilter = filter.getValue();
        assertNull(cFilter.pageSize());
        assertNull(cFilter.pageNumber());
    }

    @Test
    public void getAllUsersByTournamentIdByFilter_invalidPath_thenReturnBadRequestStatus()
        throws Exception
    {
        mockMvc.perform(get("/api/v1/participants/tournament/abc"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(tpService);
    }

    @Test
    public void getAllUsersByTournamentIdByFilter_invalidParams_thenReturnBadRequestStatus()
        throws Exception
    {
        mockMvc.perform(get("/api/v1/participants/tournament/1")
                        .param("pageSize", "-2")
                        .param("pageNumber", "abc"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(tpService);
    }
}
