package tournament.service.tournament.participant;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public record TournamentParticipantDTO(
        @NotNull
        Long participantId,

        @NotNull
        Long userId,

        @NotNull
        Long tournamentId,

        @NotNull
        TournamentParticipantStatus participantStatus,

        @NotNull
        @PastOrPresent
        LocalDateTime registrationDate,

        @NotNull
        Long createdById
) {
}
