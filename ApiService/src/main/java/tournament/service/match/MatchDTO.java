package tournament.service.match;

import jakarta.validation.constraints.NotNull;
import tournament.service.tournament.TournamentEntity;
import tournament.service.tournament.participant.TournamentParticipantEntity;

public record MatchDTO(
        @NotNull
        Long matchId,

        TournamentParticipantEntity participant1,

        TournamentParticipantEntity participant2,

        TournamentParticipantEntity winner,

        TournamentEntity tournament,

        MatchStatus matchStatus
) {
}
