package com.michaelfmnk.aldrin.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "follower_user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "followed_user_id", referencedColumnName = "userId")
    )
    private List<User> following;

    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "followed_user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "follower_user_id", referencedColumnName = "userId")
    )
    private List<User> followers;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id")
    )
    private List<Authority> authorities;

    @OneToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "postId")
    )
    private List<Post> likedPosts;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;
    private Date lastPasswordResetDate;
}
