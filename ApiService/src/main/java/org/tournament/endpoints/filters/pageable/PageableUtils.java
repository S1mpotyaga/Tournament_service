package org.tournament.endpoints.filters.pageable;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public final class PageableUtils {

    private PageableUtils() {}

    public static Pageable fromFilter(PageableRequest filter) {
        int pageSize = filter.pageSize() != null
                ? filter.pageSize()
                : 15;

        int pageNum = filter.pageNumber() != null
                ? filter.pageNumber()
                : 0;

        if (pageSize < 1 || pageSize > 100) {
            throw new IllegalArgumentException(
                    "pageSize must be between 1 and 100"
            );
        }

        if (pageNum < 0) {
            throw new IllegalArgumentException(
                    "pageNumber must be >= 0"
            );
        }

        return Pageable.ofSize(pageSize)
                .withPage(pageNum);
    }
}