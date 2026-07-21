package org.tournament.endpoints.filters;

public record TournamentParticipantFilter(
        Integer userId,
        Integer tournamentId,
        Integer pageSize,
        Integer pageNumber
) {
}
