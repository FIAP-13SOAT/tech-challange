package com.fiapchallenge.garage.shared.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class CustomPageRequest {

    private static final int DEFAULT_PAGE_SIZE = 20;

    private CustomPageRequest() {}

    public static Pageable of(Integer page, Integer size) {
        int pageNumber = page != null ? page : 0;
        int pageSize = size != null && size > 0 ? size : DEFAULT_PAGE_SIZE;
        return PageRequest.of(pageNumber, pageSize);
    }

    public static Pageable of(int page) {
        return PageRequest.of(page, DEFAULT_PAGE_SIZE);
    }
}
