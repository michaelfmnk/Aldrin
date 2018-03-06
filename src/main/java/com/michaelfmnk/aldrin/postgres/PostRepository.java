package com.michaelfmnk.aldrin.postgres;

import com.michaelfmnk.aldrin.postgres.dao.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * Selects posts that were published by users that a user
     * with a specified id is subscribed for.
     * @param username user's username, whose feed is being formed
     * @param pageable
     * @return feed for user with a specified id
     */
    List<Post> findPostByAuthorFollowersUsername(String username, Pageable pageable);


    /**
     * Selects Post with a specified id
     * @param id id of a post
     * @return post with a specified id
     */
    Post findPostById(Long id);


    /**
     * Selects posts by a user with specified username
     * @param username user's username
     * @param pageable
     * @return list of posts by a user with specified username
     */
    List<Post> findPostsByAuthorUsername(String username, Pageable pageable);


    /**
     * Selects posts by a user with specified id
     * @param id
     * @param pageable
     * @return list of posts by a user with specified id
     */
    List<Post> findPostsByAuthorId(Long id, Pageable pageable);
}
