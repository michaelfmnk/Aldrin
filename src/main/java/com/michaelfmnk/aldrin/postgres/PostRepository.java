package com.michaelfmnk.aldrin.postgres;

import com.michaelfmnk.aldrin.postgres.dao.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostByAuthorFollowersUsername(String username, Pageable pageable);
    Post findPostById(Long id);
    List<Post> findPostsByAuthorUsername(String username, Pageable pageable);
    List<Post> findPostsByAuthorId(Long id, Pageable pageable);
}
