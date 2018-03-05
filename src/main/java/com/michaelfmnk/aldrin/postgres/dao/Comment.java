package com.michaelfmnk.aldrin.postgres.dao;

import com.fasterxml.jackson.annotation.*;
import com.michaelfmnk.aldrin.jackson.JsonUserIdentity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "comments")
@Data
public class Comment {
    @Id
    Long id;

    String text;
    Date date;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    Post post;

    @JoinColumn(name = "user_id")
    @OneToOne
    @JsonUserIdentity
    User user;


}
