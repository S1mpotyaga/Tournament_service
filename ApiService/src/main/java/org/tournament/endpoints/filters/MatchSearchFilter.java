package org.tournament.endpoints.filters;

public record MatchSearchFilter(
        Integer tournamentId,
        Integer pageSize,
        Integer pageNumber
) {
}
