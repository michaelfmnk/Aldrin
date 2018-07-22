package com.michaelfmnk.aldrin.repositories;

import com.michaelfmnk.aldrin.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findPostByAuthor_FollowersUserId(Integer userId);
}
