package com.michaelfmnk.aldrin.postgres.dao;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity(name = "posts")
@Data
public class Post {
    @Id
    private Long id;
    private String title;
    private Date date;


    @JoinColumn(name = "user_id")
    @ManyToOne
    @JsonBackReference
    User author;


    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    List<Photo> photos;

}
