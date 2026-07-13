package tournament.service.match;

import jakarta.validation.constraints.NotNull;

public record MatchDTO(
        @NotNull
        Long matchId,

        @NotNull
        Long firstParticipantId,

        @NotNull
        Long secondParticipantId,

        Long winnerId,

        @NotNull
        Long tournamentId,

        @NotNull
        MatchStatus matchStatus
) {
}
