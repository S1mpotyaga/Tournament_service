package org.tournament.api;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.tournament.data.dto.MatchDTO;
import org.tournament.data.enums.MatchStatus;
import org.tournament.endpoints.controllers.MatchController;
import org.tournament.endpoints.filters.pageable.PageableFilter;
import org.tournament.endpoints.services.MatchService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchController.class)
public class MatchesControllerTest {

    @MockBean
    private MatchService matchService;
    @Autowired
    private MockMvc mockMvc;

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
        verify(matchService).getMatchById(1);
    }

    @Test
    public void getMatchById_whenMatchNotExist_thenThrowNotFound() throws Exception{

        when(matchService.getMatchById(999))
                .thenThrow(new EntityNotFoundException("Not found match with id=999"));

        mockMvc.perform(get("/api/v1/match/999"))
                .andExpect(status().isNotFound());
        verify(matchService).getMatchById(999);
    }

    @Test
    public void getMatchById_whenMatchIdNotInteger_thenThrowBadRequest() throws Exception{
        mockMvc.perform(get("/api/v1/match/abc"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(matchService);
    }

    @Test
    public void findAllMatchesByFilter_whenMatchDTOExists_thenReturnListOfMatchDTO() throws Exception{
        MatchDTO matchDTO1 = new MatchDTO();
        matchDTO1.setMatchId(1);
        matchDTO1.setMatchStatus(MatchStatus.PENDING);

        MatchDTO matchDTO2 = new MatchDTO();
        matchDTO2.setMatchId(2);
        matchDTO2.setMatchStatus(MatchStatus.FINISHED);

        when(matchService.findAllMatches(any(PageableFilter.class)))
                .thenReturn(List.of(matchDTO1, matchDTO2));

        mockMvc.perform(get("/api/v1/match/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].matchId").value(1))
                .andExpect((jsonPath("$[1].matchStatus")).value("FINISHED"));
        verify(matchService).findAllMatches(any(PageableFilter.class));
    }

    @Test
    public void findAllMatchesByFilter_whenUsePagination_thenCheckEqualsOfFilterParams() throws Exception{
        when(matchService.findAllMatches(any(PageableFilter.class)))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/match/all")
                        .param("pageSize", "2")
                        .param("pageNumber", "1"))
                .andExpect(status().isOk());

        ArgumentCaptor<PageableFilter> filterCaptor = ArgumentCaptor.forClass(PageableFilter.class);
        verify(matchService).findAllMatches(filterCaptor.capture());

        PageableFilter capturedFilter = filterCaptor.getValue();
        assertEquals(2, capturedFilter.pageSize());
        assertEquals(1, capturedFilter.pageNumber());
    }

    @Test
    public void findAllMatchesByFilter_whenMatchDTOTableIsEmpty_thenReturnEmptyList() throws Exception{
        when(matchService.findAllMatches(any(PageableFilter.class)))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/match/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void findAllMatchesByFilter_whenInvalidPageNumber_thenThrowBadRequest() {
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/api/v1/match/all")
                .param("pageNumber", "-5"))
                .andExpect(status().isBadRequest()));
        verifyNoInteractions(matchService);

    }

    @Test
    public void findAllMatchesByFilter_whenInvalidPageSize_thenThrowBadRequest() throws Exception{
        mockMvc.perform(get("/api/v1/match/all")
                .param("pageSize", "243342fde"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(matchService);
    }

    @Test
    public void findAllMatchesByFilter_whenInvalidParamsIsNull_thenCheckParamsIsNull() throws Exception{
        when(matchService.findAllMatches(any(PageableFilter.class)))
                .thenReturn(List.of());
        mockMvc.perform(get("/api/v1/match/all"))
                .andExpect(status().isOk());
        ArgumentCaptor<PageableFilter> filterCaptor = ArgumentCaptor.forClass(PageableFilter.class);
        verify(matchService).findAllMatches(filterCaptor.capture());

        PageableFilter capturedFilter = filterCaptor.getValue();
        assertNull(capturedFilter.pageSize());
        assertNull(capturedFilter.pageNumber());
    }
}
