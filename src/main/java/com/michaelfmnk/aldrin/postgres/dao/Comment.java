package com.michaelfmnk.aldrin.postgres.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "comments")
@Data
public class Comment {
    @Id
    @JsonIgnore
    Long id;

    String text;
    Date date;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    Post post;

    @JoinColumn(name = "user_id")
    @OneToOne
    @JsonIgnoreProperties({"following", "followers", "posts"})
    User user;


}
