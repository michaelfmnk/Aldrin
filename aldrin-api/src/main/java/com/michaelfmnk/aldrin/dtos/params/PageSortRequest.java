package com.michaelfmnk.aldrin.dtos.params;

import lombok.Builder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

public class PageSortRequest implements Pageable, Serializable {
    private final Sort sort;
    private final int limit;
    private final long offset;


    @Builder
    public PageSortRequest(long offset, int limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("offset can not be less then 0");
        }
        if (limit < 1) {
            throw new IllegalArgumentException("limit can not be less then 1");
        }
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    @Override
    public int getPageNumber() {
        return Long.valueOf(offset / limit).intValue();
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new PageSortRequest(offset + limit, limit, sort);
    }

    @Override
    public Pageable previousOrFirst() {
        return offset < limit
                ? new PageSortRequest(0, limit, sort)
                : new PageSortRequest(offset - limit, limit, sort);
    }

    @Override
    public Pageable first() {
        return new PageSortRequest(0, limit, sort);
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }
}
