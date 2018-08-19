package com.michaelfmnk.aldrin.utils;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SpecificationBuilder<T> {
    private List<Specification<T>> specifications = new ArrayList<>();

    public SpecificationBuilder<T> add(Specification<T> specification) {
        if (null != specification) {
            specifications.add(specification);
        }
        return this;
    }

    public Specification<T> build() {
        Specification<T> res = Specification.where(getEmptySpecification());
        if (CollectionUtils.isEmpty(specifications)) {
            return res;
        }
        for (Specification<T> spec : specifications) {
            res.and(spec);
        }
        return res;
    }

    private Specification<T> getEmptySpecification() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and();
    }
}
