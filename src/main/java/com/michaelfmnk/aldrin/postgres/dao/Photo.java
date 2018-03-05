package com.michaelfmnk.aldrin.postgres.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "photos")
@Data
public class Photo {
    @Id
    private Long id;
    private String url;


    @JoinColumn(name = "post_id")
    @ManyToOne
    @JsonBackReference
    private Post post;
}
