package com.michaelfmnk.aldrin.repositories;

import com.michaelfmnk.aldrin.entities.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    boolean existsByCommentId(Integer id);

}
