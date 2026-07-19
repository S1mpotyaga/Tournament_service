package org.tournament;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.tournament.data.dto.MatchDTO;
import org.tournament.data.enums.MatchStatus;
import org.tournament.endpoints.controllers.MatchController;
import org.tournament.endpoints.mappers.MatchMapper;
import org.tournament.endpoints.services.MatchService;

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
    public void shouldReturnMatchById_Success() throws Exception{

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
    public void shouldReturnMatchById_Error() throws Exception{

        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setMatchId(999);
        when(matchService.getMatchById(999)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/api/v1/match/999"))
                .andExpect(status().isNotFound());
    }
}
