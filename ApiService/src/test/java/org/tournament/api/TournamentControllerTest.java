package org.tournament.api;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.tournament.data.dto.TournamentDTO;
import org.tournament.endpoints.controllers.TournamentController;
import org.tournament.endpoints.services.TournamentService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TournamentControllerTest {

    @Mock
    private TournamentService tournamentService;
    @InjectMocks
    private TournamentController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getTournamentById_ifTournamentExist_thenReturnTournamentDTO() throws Exception{
        TournamentDTO tournament = new TournamentDTO();
        tournament.setTournamentId(1); tournament.setDescription("check");
        when(tournamentService.getTournamentById(1)).thenReturn(tournament);

        mockMvc.perform(get("/api/v1/tournament/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tournamentId").value(1))
                .andExpect(jsonPath("$.description").value("check"));
    }

    @Test
    void getTournamentById_ifTournamentNotExist_thenReturnNotFound() throws Exception{

        when(tournamentService.getTournamentById(999)).thenThrow(
                new EntityNotFoundException("Not found tournament with id=999")
        );

        mockMvc.perform(get("/api/v1/tournament/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTournamentById_ifIdNotInteger_thenReturnBadRequest() throws Exception{
        mockMvc.perform(get("/api/v1/tournament/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTournamentById_ifIdNotPositive_thenReturnBadRequest() throws Exception{
        mockMvc.perform(get("/api/v1/tournament/-1"))
                .andExpect(status().isBadRequest());
    }
}
