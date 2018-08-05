package com.michaelfmnk.aldrin.repositories;


import com.michaelfmnk.aldrin.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

    Page<Post> findPostByAuthor_FollowersUserId(Integer userId, Pageable pageable);

    Page<Post> findPostByAuthor_UserId(Integer userId, Pageable pageable);
}
