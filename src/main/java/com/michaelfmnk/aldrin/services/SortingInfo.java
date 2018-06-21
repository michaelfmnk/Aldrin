package com.michaelfmnk.aldrin.services;

import com.sun.tools.javac.util.StringUtils;
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
        mappings.put(StringUtils.toLowerCase(key), val);
        return this;
    }

    public Sort getSort(String key, boolean asc) {
        Direction direction = asc ? ASC : DESC;
        return new Sort(direction, mappings.getOrDefault(key, defaultValue));
    }
}
