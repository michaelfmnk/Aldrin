package com.michaelfmnk.aldrin.postgres;

import com.michaelfmnk.aldrin.postgres.dao.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     *  Selects all comments for a post with specified id
     * @param id id of a post
     * @param pageable
     * @return  list of comments for a post with specified id
     */
    List<Comment> getCommentsByPostId(Long id, Pageable pageable);


    /**
     * Selects Comment with a specified id
     * @param id id of a post
     * @return comment with a specified id
     */
    Comment getCommentById(Long id);
}
