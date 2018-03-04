package com.michaelfmnk.aldrin.postgres.dao;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity(name = "posts")
@Data
public class Post {
    @Id
    @JsonIgnore
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

    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    List<Comment> comments;


    @OneToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    @JsonIgnoreProperties({"following", "followers", "posts", "likedPosts"})
    Set<User> likes;


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
