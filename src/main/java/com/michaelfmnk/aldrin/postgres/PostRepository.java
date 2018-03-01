package com.michaelfmnk.aldrin.postgres;

import com.michaelfmnk.aldrin.postgres.dao.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostByAuthorFollowersIdInOrderByDateDesc(Long id);
    Post findPostById(Long id);
    List<Post> findPostsByAuthorUsername(String username);
    List<Post> findPostsByAuthorId(Long id);
}
