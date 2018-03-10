package com.michaelfmnk.aldrin.postgres.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "photos")
@Data
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;


    @JoinColumn(name = "post_id")
    @ManyToOne(cascade = {CascadeType.ALL})
    @JsonBackReference
    private Post post;

    public Photo() {
    }
}
