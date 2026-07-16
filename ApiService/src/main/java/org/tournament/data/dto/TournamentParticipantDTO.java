package org.tournament.data.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TournamentParticipantDTO {
    private UserDTO user;
    private TournamentDTO tournament;
    private LocalDateTime registrationDate;
}
