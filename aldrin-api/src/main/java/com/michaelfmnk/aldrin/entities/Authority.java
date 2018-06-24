package com.michaelfmnk.aldrin.entities;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "authorities")
@Data
public class Authority {
    @Id
    private Integer id;
    private String auth;
}
