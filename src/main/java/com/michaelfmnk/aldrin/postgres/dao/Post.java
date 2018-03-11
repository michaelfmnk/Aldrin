package com.michaelfmnk.aldrin.postgres.dao;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.michaelfmnk.aldrin.jackson.JsonUserIdentity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Entity(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 300, message = "title must not contain more then 300 characters")
    private String title;
    private Date date;


    @JoinColumn(name = "user_id")
    @ManyToOne
    @JsonBackReference
    private User author;


    @OneToMany(mappedBy = "post", cascade = {CascadeType.ALL})
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

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }

    public boolean hasLikeByUser(Long id) {
        for (User liked :
                likes) {
            if (liked.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasLikeByUser(String username) {
        for (User liked :
                likes) {
            if (liked.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void setPhoto(Photo photo){
        List<Photo> photos = Arrays.asList(photo);
        this.photos = photos;
    }


}
