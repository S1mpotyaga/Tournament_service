package org.tournament.endpoints.filters.pageable;

public record IdPaginationFilter(
        Integer Id,
        Integer pageSize,
        Integer pageNumber
) implements PageableRequest {}