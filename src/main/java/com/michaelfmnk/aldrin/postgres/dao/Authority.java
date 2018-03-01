package com.michaelfmnk.aldrin.postgres.dao;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "authorities")
@Data
public class Authority {
    @Id
    private Long id;
    private String auth;

}
