package org.tournament.endpoints.filters;

public record UserSearchFilter(
        Integer userId,
        String nick,
        Integer pageSize,
        Integer pageNumber
) {
}
