package com.michaelfmnk.aldrin.postgres.dao;


import com.fasterxml.jackson.annotation.*;
import com.michaelfmnk.aldrin.jackson.JsonUserIdentity;
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
    private User author;


    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    private List<Photo> photos;

    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    private List<Comment> comments;


    @OneToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @JsonUserIdentity
    private List<User> likes;


    public boolean hasLikeByUser(Long id){
        for (User liked :
                likes) {

            if (liked.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public boolean hasLikeByUser(String username){
        for (User liked :
                likes) {
            System.out.println("name"+ liked.getUsername());
            if (liked.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }


}
