package org.tournament.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.tournament.data.dto.MatchDTO;
import org.tournament.data.dto.UserDTO;
import org.tournament.endpoints.controllers.TournamentParticipantController;
import org.tournament.endpoints.filters.TournamentParticipantFilter;
import org.tournament.endpoints.services.MatchService;
import org.tournament.endpoints.services.TournamentParticipantService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class TournamentParticipantControllerTest {
    @Mock
    private TournamentParticipantService tpService;
    @Mock
    private MatchService matchService;
    @InjectMocks
    private TournamentParticipantController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    // api/v1/participant/user

    @Test
    public void getAllMatchesByUserIdByFilter_withParams_thenReturnAllUsersDTO()
            throws Exception
    {
        MatchDTO match1 = new MatchDTO(); MatchDTO match2 = new MatchDTO();
        when(matchService.getAllMatchesByUserIdByFilter(any(TournamentParticipantFilter.class)))
                .thenReturn(List.of(match1, match2));
        mockMvc.perform(get("/api/v1/participants/user/1")
                        .param("tournamentId", "1")
                        .param("pageSize", "10")
                        .param("pageNumber", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        ArgumentCaptor<TournamentParticipantFilter> filter = ArgumentCaptor.forClass(TournamentParticipantFilter.class);
        verify(matchService).getAllMatchesByUserIdByFilter(filter.capture());
        TournamentParticipantFilter cFilter = filter.getValue();
        assertEquals(1, cFilter.userId());
        assertEquals(1, cFilter.tournamentId());
        assertEquals(10, cFilter.pageSize());
        assertEquals(5, cFilter.pageNumber());
    }

    @Test
    public void getAllMatchesByUserIdByFilter_withOutParams_thenReturnAllUsersDTO()
            throws Exception
    {
        MatchDTO match1 = new MatchDTO(); MatchDTO match2 = new MatchDTO();
        when(matchService.getAllMatchesByUserIdByFilter(any(TournamentParticipantFilter.class)))
                .thenReturn(List.of(match1, match2));
        mockMvc.perform(get("/api/v1/participants/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        ArgumentCaptor<TournamentParticipantFilter> filter = ArgumentCaptor.forClass(TournamentParticipantFilter.class);
        verify(matchService).getAllMatchesByUserIdByFilter(filter.capture());
        TournamentParticipantFilter cFilter = filter.getValue();
        assertNull(cFilter.tournamentId());
        assertNull(cFilter.pageSize());
        assertNull(cFilter.pageNumber());
    }

    @Test
    public void getAllMatchesByUserIdByFilter_invalidPath_thenReturnBadRequestStatus()
            throws Exception
    {
        mockMvc.perform(get("/api/v1/participants/user/abc"))
                .andExpect(status().isBadRequest());
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
    }

    // api/v1/participant/tournament

    @Test
    public void getAllUsersByTournamentIdByFilter_withParams_thenReturnAllUsersDTO()
            throws Exception
    {
        UserDTO user1 = new UserDTO(); UserDTO user2 = new UserDTO();
        when(tpService.getAllUsersByTournamentIdByFilter(any(TournamentParticipantFilter.class)))
                .thenReturn(List.of(user1, user2));
        mockMvc.perform(get("/api/v1/participants/tournament/1")
                .param("pageSize", "1")
                .param("pageNumber", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        ArgumentCaptor<TournamentParticipantFilter> filter = ArgumentCaptor.forClass(TournamentParticipantFilter.class);
        verify(tpService).getAllUsersByTournamentIdByFilter(filter.capture());
        TournamentParticipantFilter cFilter = filter.getValue();
        assertEquals(1, cFilter.pageSize());
        assertEquals(2, cFilter.pageNumber());
    }

    @Test
    public void getAllUsersByTournamentIdByFilter_withOutParams_thenReturnAllUsersDTO()
        throws Exception
    {
        UserDTO user1 = new UserDTO(); UserDTO user2 = new UserDTO();
        when(tpService.getAllUsersByTournamentIdByFilter(any(TournamentParticipantFilter.class)))
                .thenReturn(List.of(user1, user2));
        mockMvc.perform(get("/api/v1/participants/tournament/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
        ArgumentCaptor<TournamentParticipantFilter> filter = ArgumentCaptor.forClass(TournamentParticipantFilter.class);
        verify(tpService).getAllUsersByTournamentIdByFilter(filter.capture());
        TournamentParticipantFilter cFilter = filter.getValue();
        assertNull(cFilter.pageSize());
        assertNull(cFilter.pageNumber());
    }

    @Test
    public void getAllUsersByTournamentIdByFilter_invalidPath_thenReturnBadRequestStatus()
        throws Exception
    {
        mockMvc.perform(get("/api/v1/participants/tournament/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllUsersByTournamentIdByFilter_invalidParams_thenReturnBadRequestStatus()
        throws Exception
    {
        mockMvc.perform(get("/api/v1/participants/tournament/1")
                        .param("pageSize", "-2")
                        .param("pageNumber", "abc"))
                .andExpect(status().isBadRequest());
    }
}
