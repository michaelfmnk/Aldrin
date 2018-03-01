package com.michaelfmnk.aldrin.postgres.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "users")
@Data
public class User {
    @Id
    @JsonIgnore
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    @JsonIgnore
    private String password;
    private String email;


    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subscribed_for_id", referencedColumnName = "id")
    )
    @ManyToMany
    @JsonIgnoreProperties({"following", "followers", "posts"})
    private List<User> following;

    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "subscribed_for_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id")
    )
    @ManyToMany
    @JsonIgnoreProperties({"following", "followers", "posts"})
    private List<User> followers;

    @JoinTable(
            name = "user_auth",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "auth_id", referencedColumnName = "id")
    )
    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Authority> authorities;

    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    private List<Post> posts;

    @JsonIgnore
    private Date lastPasswordResetDate;
}
