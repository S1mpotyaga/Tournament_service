package org.tournament;

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
import org.tournament.data.dto.MatchDTO;
import org.tournament.data.enums.MatchStatus;
import org.tournament.endpoints.controllers.MatchController;
import org.tournament.endpoints.filters.MatchSearchFilter;
import org.tournament.endpoints.mappers.MatchMapper;
import org.tournament.endpoints.services.MatchService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchesTest {

    @Mock
    private MatchService matchService;

    @Mock
    private MatchMapper matchMapper;

    @InjectMocks
    private MatchController matchController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(matchController).build();
    }

    @Test
    public void getMatchById_whenMatchExist_thenReturnMatchDTO() throws Exception{

        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setMatchId(1);
        matchDTO.setMatchStatus(MatchStatus.PENDING);
        when(matchService.getMatchById(1)).thenReturn(matchDTO);

        mockMvc.perform(get("/api/v1/match/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matchId").value(1))
                .andExpect(jsonPath("$.matchStatus").value("PENDING"));
    }

    @Test
    public void getMatchById_whenMatchNotExist_thenThrowNotFound() throws Exception{

        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setMatchId(999);
        when(matchService.getMatchById(999))
                .thenThrow(new EntityNotFoundException("Not found match with id=999"));

        mockMvc.perform(get("/api/v1/match/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getMatchById_whenMatchIdNotInteger_thenThrowBadRequest() throws Exception{
        mockMvc.perform(get("/api/v1/match/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAllMatchesByFilter_whenMatchDTOExists_thenReturnListOfMatchDTO() throws Exception{
        MatchDTO matchDTO1 = new MatchDTO();
        matchDTO1.setMatchId(1);
        matchDTO1.setMatchStatus(MatchStatus.PENDING);

        MatchDTO matchDTO2 = new MatchDTO();
        matchDTO2.setMatchId(2);
        matchDTO2.setMatchStatus(MatchStatus.FINISHED);

        when(matchService.getAllMatchByFilter(any(MatchSearchFilter.class)))
                .thenReturn(List.of(matchDTO1, matchDTO2));

        mockMvc.perform(get("/api/v1/match/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].matchId").value(1))
                .andExpect((jsonPath("$[1].matchStatus")).value("FINISHED"));
    }

    @Test
    public void findAllMatchesByFilter_whenUsePagination_thenCheckEqualsOfFilterParams() throws Exception{
        when(matchService.getAllMatchByFilter(any(MatchSearchFilter.class)))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/match/all")
                        .param("pageSize", "2")
                        .param("pageNumber", "1"))
                .andExpect(status().isOk());

        ArgumentCaptor<MatchSearchFilter> filterCaptor = ArgumentCaptor.forClass(MatchSearchFilter.class);
        verify(matchService).getAllMatchByFilter(filterCaptor.capture());

        MatchSearchFilter capturedFilter = filterCaptor.getValue();
        assertEquals(2, capturedFilter.pageSize());
        assertEquals(1, capturedFilter.pageNumber());
    }

    @Test
    public void findAllMatchesByFilter_whenMatchDTOTableIsEmpty_thenReturnEmptyList() throws Exception{
        when(matchService.getAllMatchByFilter(any(MatchSearchFilter.class)))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/match/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void findAllMatchesByFilter_whenMatchDTOIsExist_thenReturnMatchDTOId() throws Exception{
        when(matchService.getAllMatchByFilter(any(MatchSearchFilter.class)))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/match/all")
                        .param("tournamentId", "42"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
        ArgumentCaptor<MatchSearchFilter> filterCaptor = ArgumentCaptor.forClass(MatchSearchFilter.class);
        verify(matchService).getAllMatchByFilter(filterCaptor.capture());

        MatchSearchFilter capturedFilter = filterCaptor.getValue();
        assertEquals(42, capturedFilter.tournamentId());
    }

    @Test
    public void findAllMatchesByFilter_whenInvalidPageNumber_thenThrowBadRequest() throws Exception{
        mockMvc.perform(get("/api/v1/match/all")
                .param("pageNumber", "-5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAllMatchesByFilter_whenInvalidPageSize_thenThrowBadRequest() throws Exception{
        mockMvc.perform(get("/api/v1/match/all")
                .param("pageSize", "243342fde"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAllMatchesByFilter_whenInvalidParamsIsNull_thenCheckParamsIsNull() throws Exception{
        when(matchService.getAllMatchByFilter(any(MatchSearchFilter.class)))
                .thenReturn(List.of());
        mockMvc.perform(get("/api/v1/match/all"))
                .andExpect(status().isOk());
        ArgumentCaptor<MatchSearchFilter> filterCaptor = ArgumentCaptor.forClass(MatchSearchFilter.class);
        verify(matchService).getAllMatchByFilter(filterCaptor.capture());

        MatchSearchFilter capturedFilter = filterCaptor.getValue();
        assertNull(capturedFilter.pageSize());
        assertNull(capturedFilter.pageNumber());
    }
}
