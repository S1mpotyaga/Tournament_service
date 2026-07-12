package tournament.service.tournament.participant;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import tournament.service.tournament.TournamentEntity;
import tournament.service.user.UserEntity;

import java.time.LocalDateTime;

public record TournamentParticipantDTO(
        @NotNull
        Long participantId,

        @NotNull
        UserEntity user,

        @NotNull
        TournamentEntity tournament,

        @NotNull
        TournamentParticipantStatus participantStatus,

        @NotNull
        @PastOrPresent
        LocalDateTime registrationDate,

        @NotNull
        UserEntity createdBy
) {
}
