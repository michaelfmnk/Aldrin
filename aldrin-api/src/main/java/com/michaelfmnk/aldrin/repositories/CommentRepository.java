package com.michaelfmnk.aldrin.repositories;

import com.michaelfmnk.aldrin.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    boolean existsByCommentId(Integer id);

}
