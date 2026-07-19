package org.tournament.data.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentParticipantDTO {
    private Integer id;
    private UserDTO user;
    private TournamentDTO tournament;
    private LocalDateTime registrationDate;
}
