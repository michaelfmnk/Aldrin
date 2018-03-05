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
    private Long id;
    private String text;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    @JoinColumn(name = "user_id")
    @OneToOne
    @JsonUserIdentity
    private User user;


}
