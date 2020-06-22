package com.drew.surfphotos.model;

import com.drew.surfphotos.exception.ValidationException;

/**
 * Объект, который представляет постраничное отображение
 */
public class Pageable {
    private final int page;
    private final int limit;

    public Pageable(int limit) {
        this(1, limit);
    }

    public Pageable(int page, int limit) {
        if (page < 1){
            throw new ValidationException("Invalid page value. Should be >= 1");
        }
        if(limit < 1){
            throw new ValidationException("Invalid limit value. Should be >= 1");
        }
        this.page = page;
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset(){
        return (page - 1) * limit;
    }

    @Override
    public String toString() {
        return "Pageable{" +
                "page=" + page +
                ", limit=" + limit +
                '}';
    }
}
