package com.michaelfmnk.aldrin.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    public static final String USER_ID = "userId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String login;
    private String firstName;
    private String lastName;
    private String password;

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
            name = "users_authorities",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "authorityId")
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

    @CreationTimestamp
    private Date lastPasswordResetDate;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    private boolean enabled;
}
