package org.tournament.endpoints.filters;

public record UserSearchFilter(
        Integer userId,
        Integer pageSize,
        Integer pageNumber
) {
}
