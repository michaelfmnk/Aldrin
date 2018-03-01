package com.michaelfmnk.aldrin.postgres.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "authorities")
@Data
public class Authority {
    @Id
    @JsonIgnore
    private Long id;
    private String auth;

}
