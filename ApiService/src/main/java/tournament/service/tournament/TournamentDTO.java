package tournament.service.tournament;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TournamentDTO(
        @NotNull
        Long tournamentId,

        @NotNull
        String tournamentName,

        String tournamentDescription,

        @NotNull
        TournamentBracketType tournamentBracketType,

        @NotNull
        TournamentStatus tournamentStatus,

        @NotNull
        @FutureOrPresent
        LocalDateTime registrationStart,

        @NotNull
        @FutureOrPresent
        LocalDateTime registrationEnd,

        @NotNull
        @PastOrPresent
        LocalDateTime createdAt
) {
}
