package com.michaelfmnk.aldrin.services;


import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

public class SortingInfo {
    private Map<String, String> mappings = new HashMap<>();
    private final String defaultValue;

    public SortingInfo(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public SortingInfo mapping(String key, String val) {
        if (Objects.isNull(key) || Objects.isNull(val)) {
            throw new IllegalArgumentException();
        }
        mappings.put(key.toLowerCase(), val); //todo
        return this;
    }

    public Sort getSort(String key, boolean asc) {
        Direction direction = asc ? ASC : DESC;
        return new Sort(direction, mappings.getOrDefault(key, defaultValue));
    }

    public Sort getDefaultSort(boolean asc) {
        Direction direction = asc ? ASC : DESC;
        return new Sort(direction, mappings.getOrDefault(null, defaultValue));
    }

    public Sort getDefaultSort() {
        return getDefaultSort(true);
    }
}
