package org.tournament.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.tournament.data.enums.TournamentStatus;
import org.tournament.data.enums.TournamentBracketType;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TournamentDTO {
    private String name;
    private String description;
    private TournamentBracketType tournamentBracketType;
    private TournamentStatus tournamentStatus;
    private LocalDateTime createdDate;
}
