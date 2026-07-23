package org.tournament.api;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.tournament.data.dto.TournamentDTO;
import org.tournament.endpoints.ConverterException;
import org.tournament.endpoints.controllers.TournamentController;
import org.tournament.endpoints.services.TournamentService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TournamentController.class)
@AutoConfigureMockMvc
public class TournamentControllerTest {

    @MockBean
    private TournamentService tournamentService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTournamentById_ifTournamentExist_thenReturnTournamentDTO() throws Exception{
        TournamentDTO tournament = new TournamentDTO();
        tournament.setTournamentId(1); tournament.setDescription("check");
        when(tournamentService.getTournamentById(1)).thenReturn(tournament);

        mockMvc.perform(get("/api/v1/tournament/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tournamentId").value(1))
                .andExpect(jsonPath("$.description").value("check"));
        verify(tournamentService).getTournamentById(1);
    }

    @Test
    void getTournamentById_ifTournamentNotExist_thenReturnNotFound() throws Exception{

        when(tournamentService.getTournamentById(999)).thenThrow(
                new EntityNotFoundException("Not found tournament with id=999")
        );

        mockMvc.perform(get("/api/v1/tournament/999"))
                .andExpect(status().isNotFound());
        verify(tournamentService).getTournamentById(999);

    }

    @Test
    void getTournamentById_ifIdNotInteger_thenReturnBadRequest() throws Exception{
        mockMvc.perform(get("/api/v1/tournament/abc"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(tournamentService);
    }

    @Test
    void getTournamentById_ifIdNotPositive_thenReturnBadRequest() throws Exception{
        assertThrows(ServletException.class, () ->
                        mockMvc.perform(get("/api/v1/tournament/-1")));
        verifyNoInteractions(tournamentService);
    }

    @Test
    void getTournamentById_ifConverterException_thenReturnBadRequest() throws Exception{
        when(tournamentService.getTournamentById(1))
                .thenThrow(new ConverterException("mapping error"));
        mockMvc.perform(get("/api/v1/tournament/1"))
                .andExpect(status().isBadRequest());
        verify(tournamentService).getTournamentById(1);

    }
}
