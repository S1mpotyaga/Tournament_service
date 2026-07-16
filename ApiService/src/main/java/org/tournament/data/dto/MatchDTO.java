package org.tournament.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.tournament.data.enums.MatchStatus;

@Data
@Builder
@AllArgsConstructor
public class MatchDTO {
    private UserDTO player1;
    private UserDTO player2;
    private TournamentDTO tournament;
    private UserDTO winner;
    private MatchStatus matchStatus;
}
