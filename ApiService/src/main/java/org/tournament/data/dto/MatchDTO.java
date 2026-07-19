package org.tournament.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tournament.data.enums.MatchStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchDTO {
    private Integer matchId;
    private UserDTO player1;
    private UserDTO player2;
    private TournamentDTO tournament;
    private UserDTO winner;
    private MatchStatus matchStatus;
}
