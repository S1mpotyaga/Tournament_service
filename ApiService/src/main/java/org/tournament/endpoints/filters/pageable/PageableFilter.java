package org.tournament.endpoints.filters.pageable;

public record PageableFilter(
        Integer pageSize,
        Integer pageNumber
) implements PageableRequest {}
