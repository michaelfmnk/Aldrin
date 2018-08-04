package com.michaelfmnk.aldrin.repositories;


import com.michaelfmnk.aldrin.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

    List<Post> findPostByAuthor_FollowersUserId(Integer userId);

    Page<Post> findPostByAuthor_UserId(Integer userId, Pageable pageable);
}
