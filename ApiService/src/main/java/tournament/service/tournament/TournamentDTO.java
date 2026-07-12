package tournament.service.tournament;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TournamentDTO(
        @NotNull
        Long tournamentId,

        @NotNull
        String tournamentName,

        @NotNull
        String tournamentDescription,

        TournamentBracketType tournamentBracketType,
        TournamentStatus tournamentStatus,

        @NotNull
        @FutureOrPresent
        LocalDate registrationStart,

        @NotNull
        @FutureOrPresent
        LocalDate registrationEnd,

        @NotNull
        @PastOrPresent
        LocalDate createdAt
) {
}
