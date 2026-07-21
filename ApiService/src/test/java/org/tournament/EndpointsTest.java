package org.tournament;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("local")
public class EndpointsTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createUser() throws Exception{
        mockMvc.perform(
                        post("/api/v1/user/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                        {
                            "fullName": "player4",
                            "role": "GUEST",
                            "password": "test4password",
                            "nick": "player4",
                            "email": "test4@gmail.com"
                        }
                        """)
                )
                .andExpect(status().isCreated());
    }

    @Test
    public void createTournament() throws Exception{
        mockMvc.perform(
                post("/api/v1/tournament/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "tournament2",
                            "description": "test tournament",
                            "tournamentBracketType": "GROUP_STEP_AND_PLAYOFF",
                            "tournamentStatus": "IN_PROGRESS"
                        }
                        """)
        ).andExpect(status().isCreated());
    }
}
